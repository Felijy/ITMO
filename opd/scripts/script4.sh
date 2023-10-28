cd ../lab0

echo "--4.1--"
wc -m meganium0/ampharos quilava9/lombre 2>/dev/null | sort

echo "--4.2--"
ls -lR | grep -e '\<m' | grep -v './' | sort -k2

echo "--4.3--"
cat -n quilava9/lombre 2>&1 | grep -v 'g$'
cat -n quilava9/tirtouga 2>&1 | grep -v 'g$'
cat -n quilava9/spoink 2>&1 | grep -v 'g$'
cat -n quilava9/magby 2>&1 | grep -v 'g$'
cat -n weezing6/pichu 2>&1 | grep -v 'g$'

echo "--4.4--"
ls -l weezing6/ | sort -nk2 2>&1

echo "--4.5--"
ls -Rl | grep 'ga'| grep -v './' | sort -nk5 | head -4

echo "--4.6--"
ls -Rl | grep 'u$' | sort -nk8 2>&1
