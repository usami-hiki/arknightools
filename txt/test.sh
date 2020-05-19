#!/bin/sh

if [ $# -ne 2 ]; then
  echo -n FOLDER_STR: 
  read folder 
  echo -n FILE_STR: 
  read file 

else
  folder=$1
  file=$2

fi


echo wget https://aceship.github.io/AN-EN-Tags/spineassets/character/${folder}/front/${file}.skel


char_112_siege