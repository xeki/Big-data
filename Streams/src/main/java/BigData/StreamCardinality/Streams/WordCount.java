package BigData.StreamCardinality.Streams;
import java.io.IOException;
//import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
//import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, NullWritable>{

     private Text word = new Text();
	  //private IntWritable one = new IntWritable(1);

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens()) {
        word.set(itr.nextToken());
        //context.write(word, NullWritable.get());
    	 context.write(word, NullWritable.get());
      }
    }
  }
 public static class Combiner
  extends Reducer<Text,NullWritable,Text,NullWritable> {
public void reduce(Text key, Iterable<NullWritable> values,
                  Context context
                  ) throws IOException, InterruptedException {
	

 context.write(key, NullWritable.get());
}
}
  public static class IntSumReducer
       extends Reducer<Text,NullWritable,Text,NullWritable> {
	  private Text word = new Text();
    public void reduce(Text key, Iterable<NullWritable> values,
                       Context context
                       ) throws IOException, InterruptedException {
    	 
        word.set("1");
             
      context.write(word, NullWritable.get());
    }
  }
  
  public static void main(String[] args) throws Exception {
	
	Configuration conf = new Configuration();
	Job job = Job.getInstance(conf, "distinct word count");
    job.setJarByClass(WordCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(Combiner.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(NullWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
        
  }
}