git branch --list >> list.txt
array=(`cat list.txt | tr -d '' | tr -d '*'`)

echo "sync with master"
for i in "${array[@]}" ; do
    if [ i != "master" ]; then
      echo "======= Merge MASTER into $i ======= \r"
      git checkout $i;
      git merge master;
      git pull;
      git push;
    fi
done

rm list.txt