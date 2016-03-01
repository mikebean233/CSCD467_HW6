import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCount {

  public static class TokenizerMapper extends Mapper<Object, Text, Text, Text>{
    
    private final static IntWritable one = new IntWritable(1);
    private Text targetAndPath = new Text();
    private Text lineNo = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
      context.getFileClassPaths();

      StringTokenizer itr = new StringTokenizer(value.toString());
      String path = ((FileSplit) context.getInputSplit()).getPath().toString();
      String target = context.getConfiguration().get("target");
      String fileLength = "0+" + context.getInputSplit().getLength();
      int occurenceCount = 0;

      while (itr.hasMoreTokens()) {
        String thisToken = itr.nextToken();
        if(thisToken.toLowerCase().equals(target))
          occurenceCount++;
      }  
      if(occurentCount !=0){
        targetAndPath.set(target + "\t" + path + ":" + fileLength + ", ");// + lineNo);
        lineNo.set(context.getConfiguration().get("target"));
        context.write(word, lineNo);//new IntWritable(occurenceCount == 0 ? 0 : 1));  
      }
    }
  }
  
  public static class IntSumReducer 
       extends Reducer<Text,Text,Text,Text> {
    private Text result = new Text();

    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (Text val : values) {
        //sum += val.get();
      //if(sum > 0){
        result.set(val);
        context.write(key, result);
      //}
      }
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

    if (otherArgs.length < 3) {
      System.err.println("Usage: wordcount <pattern> <in> [<in>...] <out>");
      System.exit(2);
    }

    String pattern = args[0];
    conf.set("target", pattern);

    Job job = Job.getInstance(conf, "word count");// //Job(conf, "word count");
    job.setJarByClass(WordCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    for (int i = 1; i < otherArgs.length - 1; ++i) {
      FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
    }

    FileOutputFormat.setOutputPath(job,
      new Path(otherArgs[otherArgs.length - 1]));

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}