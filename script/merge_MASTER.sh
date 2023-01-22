
printf "merge start \r"
curBranch=$(git rev-parse --abbrev-ref HEAD)
printf "======= Merge from $curBranch into master ======= \r"

git checkout master;
printf "pull master... \r"
git pull;
printf "merge $curBranch... \r"
git merge $curBranch;
printf "push master... \r"
git push;
printf "\r"

printf "======= merge into master done.. return to $curBranch ======= \r"
git checkout $curBranch;