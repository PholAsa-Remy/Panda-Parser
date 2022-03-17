input=1
run ()
{
  time ./panda-parser build --input-dir "src/test/resources/Big-files-test/$input" --output-dir "out_$input"
}

input=1
run;