#!/bin/bash
# int-or-string.sh
a=1
b=1
for i in {1..28} ; do
  pdftk input.pdf cat $a output "page-$b.pdf"
  pdftoppm -jpeg page-$b.pdf page-$b.jpeg
  rm "page-$b.pdf"
  mv page-$b.jpeg-1.jpg page-$b.jpeg
  rm "page-$b.jpeg-1.jpg"
  let a=a+1
  let b=b+1
done

