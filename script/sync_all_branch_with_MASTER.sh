git branch --list >> list.txt
array=(`cat list.txt | tr -d '' | tr -d '*'`)

for i in "${array[@]}" ; do
    if [ i != "master" ]; then
      echo "======= Sync from MASTER into $i ======= \r"
      git checkout $i;
      git merge master;
      git pull;
      git push;
    fi
done

git checkout master;

rm list.txt