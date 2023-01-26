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

rm list.txt