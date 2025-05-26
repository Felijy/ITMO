rm -rf src
rm -rf .git
rm -rf .gitignore

git init

echo "zips/" >> .gitignore

unzip zips/commit0.zip -d src

git config --local user.name "user-1"
git config --local user.email f@f.com

# r0
git add .
git commit -m "r0"

# r1
rm -rf src
unzip zips/commit1.zip -d src
git add .
git commit -m "r1"

# r2
rm -rf src
unzip zips/commit2.zip -d src
git add .
git commit -m "r2"
git switch -c branch2

git config --local user.name "user-2"
git config --local user.email g@g.com

# r3
rm -rf src
unzip zips/commit3.zip -d src
git add .
git commit -m "r3"
git switch -c branch3

# r4
rm -rf src
unzip zips/commit4.zip -d src
git add .
git commit -m "r4"
git switch -c branch4

# r5
rm -rf src
unzip zips/commit5.zip -d src
git add .
git commit -m "r5"

# r6
git switch branch3
rm -rf src
unzip zips/commit6.zip -d src
git add .
git commit -m "r6"
git switch -c branch6

git config --local user.name "user-1"
git config --local user.email f@f.com

# r7
rm -rf src
unzip zips/commit7.zip -d src
git add .
git commit -m "r7"

git config --local user.name "user-2"
git config --local user.email g@g.com

# r8
git switch branch4
rm -rf src
unzip zips/commit8.zip -d src
git add .
git commit -m "r8"

git config --local user.name "user-1"
git config --local user.email f@f.com

# r9
git switch master
rm -rf src
unzip zips/commit9.zip -d src
git add .
git commit -m "r9"
git switch -c branch9

git config --local user.name "user-2"
git config --local user.email g@g.com

# r10
rm -rf src
unzip zips/commit10.zip -d src
git add .
git commit -m "r10"

git config --local user.name "user-1"
git config --local user.email f@f.com

# r11
git switch branch6
rm -rf src
unzip zips/commit11.zip -d src
git add .
git commit -m "r11"

git config --local user.name "user-2"
git config --local user.email g@g.com

# r12
git switch branch3
rm -rf src
unzip zips/commit12.zip -d src
git add .
git commit -m "r12"

# r13
git switch branch9
rm -rf src
unzip zips/commit13.zip -d src
git add .
git commit -m "r13"
git switch -c branch13

# r14
rm -rf src
unzip zips/commit14.zip -d src
git add .
git commit -m "r14"

# r15
git switch branch9
rm -rf src
unzip zips/commit15.zip -d src
git add .
git commit -m "r15"

# r16
git switch branch4
rm -rf src
unzip zips/commit16.zip -d src
git add .
git commit -m "r16"
git switch -c branch16

git config --local user.name "user-1"
git config --local user.email f@f.com

# r17
rm -rf src
unzip zips/commit17.zip -d src
git add .
git commit -m "r17"

# r18
git switch master
rm -rf src
unzip zips/commit18.zip -d src
git add .
git commit -m "r18"

git config --local user.name "user-2"
git config --local user.email g@g.com

# r19
git switch branch3
rm -rf src
unzip zips/commit19.zip -d src
git add .
git commit -m "r19"
git switch -c branch19

# r20
rm -rf src
unzip zips/commit20.zip -d src
git add .
git commit -m "r20"

# r21
git switch branch3
rm -rf src
unzip zips/commit21.zip -d src
git add .
git commit -m "r21"

# r22
git switch branch9
rm -rf src
unzip zips/commit22.zip -d src
git add .
git commit -m "r22"
git switch -c branch22

git config --local user.name "user-1"
git config --local user.email f@f.com

# r23
rm -rf src
unzip zips/commit23.zip -d src
git add .
git commit -m "r23"

# r24
git switch master
rm -rf src
unzip zips/commit24.zip -d src
git add .
git commit -m "r24"

git config --local user.name "user-2"
git config --local user.email g@g.com

# r25
git switch branch9
rm -rf src
unzip zips/commit25.zip -d src
git add .
git commit -m "r25"
git switch -c branch25

# r26
rm -rf src
unzip zips/commit26.zip -d src
git add .
git commit -m "r26"

git config --local user.name "user-1"
git config --local user.email f@f.com

# r27
git switch branch16
rm -rf src
unzip zips/commit27.zip -d src
git add .
git commit -m "r27"

# r28
git switch branch6
rm -rf src
unzip zips/commit28.zip -d src
git add .
git commit -m "r28"
git merge branch16
# разрешение конфликтов A H
read -p "Разрешение конфликта, ENTER..."
git add .
git commit -m "r28"

git config --local user.name "user-2"
git config --local user.email g@g.com

# r29
git switch branch19
rm -rf src
unzip zips/commit29.zip -d src
git add .
git commit -m "r29"
git merge branch6
# разрешение конфликтов A H
read -p "Разрешение конфликта, ENTER..."
git add .
git commit -m "r29"

# r30
git switch branch25
rm -rf src
unzip zips/commit30.zip -d src
git add .
git commit -m "r30"
git merge branch19

# r31
git switch branch9
rm -rf src
unzip zips/commit31.zip -d src
git add .
git commit -m "r31"
git merge branch25
# разрешение конфликтов A H
read -p "Разрешение конфликта, ENTER..."
git add .
git commit -m "r31"

# r32
git switch branch4
rm -rf src
unzip zips/commit32.zip -d src
git add .
git commit -m "r32"
git merge branch9
# разрешение конфликтов A H
read -p "Разрешение конфликта, ENTER..."
git add .
git commit -m "r32"

git config --local user.name "user-1"
git config --local user.email f@f.com

# r33
git switch branch22
rm -rf src
unzip zips/commit33.zip -d src
git add .
git commit -m "r33"
git merge branch4
# разрешение конфликтов *
read -p "Разрешение конфликта, ENTER..."
git add .
git commit -m "r33"

git config --local user.name "user-2"
git config --local user.email g@g.com

# r34
git switch branch3
rm -rf src
unzip zips/commit34.zip -d src
git add .
git commit -m "r34"
git merge branch22
# разрешение конфликтов *
read -p "Разрешение конфликта, ENTER..."
git add .
git commit -m "r34"
git switch -c branch34

# r35
rm -rf src
unzip zips/commit35.zip -d src
git add .
git commit -m "r35"
git merge branch2

# r36
git switch branch13
rm -rf src
unzip zips/commit36.zip -d src
git add .
git commit -m "r36"
git merge branch34

# r37
rm -rf src
unzip zips/commit37.zip -d src
git add .
git commit -m "r37"

# r38
git switch branch4
rm -rf src
unzip zips/commit36.zip -d src
git add .
git commit -m "r38"
git merge branch34

git config --local user.name "user-1"
git config --local user.email f@f.com

# r39
git switch master
rm -rf src
unzip zips/commit39.zip -d src
git add .
git commit -m "r39"
git merge branch4
read -p "Разрешение конфликта, ENTER..."
git add .
git commit -m "r39"
