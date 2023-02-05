git branch --list >> list.txt
array=(`cat list.txt | tr -d '' | tr -d '*'`)


printf "go do MASTER branch \r"
git checkout master;

for i in "${array[@]}" ; do
    if [ i != "master" ]; then
      echo "======= Merge from $i into MASTER ======= \r"
      git merge $i;
    fi
done

git pull;
git push;


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

rm list.txt;

git checkout master;