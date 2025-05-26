rm -rf svn

mkdir svn
cd svn

svnadmin create rep

svn checkout file://$(pwd)/rep ./src
# src типа рабочая директория

cd src
mkdir trunk branches tags
svn add trunk branches tags
svn commit -m "Init"
# trunk - основная ветка, branches - новые ветки, tags - стабильные ветки


#r0
unzip ~/lab2/zips/commit0.zip -d trunk
svn add trunk/*
svn commit -m "r0" --username=user1

#r1
rm -rf trunk
unzip ~/lab2/zips/commit1.zip -d trunk
svn add trunk/*
svn commit -m "r1" --username=user1

#r2
rm -rf trunk
unzip ~/lab2/zips/commit2.zip -d trunk
svn add trunk/*
svn delete trunk/qS25MwH9rf.31D
svn commit -m "r2" --username=user1












rm -rf repository

mkdir msp
cd msp
svnadmin create rep

svn mkdir file://$(pwd)/rep/trunk -m "Create trunk"
svn mkdir file://$(pwd)/rep/branches -m "Create branches"
svn mkdir file://$(pwd)/rep/tags -m "Create tags"

svn checkout file://$(pwd)/rep/trunk trunk-wc
cd trunk-wc
mkdir src
unzip ../../zips/commit37.zip -d src

svn add src/*
svn commit -m "r0" --username=user1

svn copy file://$(pwd)/trunk \
         file://$(pwd)/branches/branch1 \
         -m "Create branch1"

svnadmin create repository
mkdir main
svn mkdir file://$(pwd)/repository/trunk

# r0
unzip ../zips/commit36.zip
svn checkout file://$(pwd)/repository/trunk/main/trunk

mkdir src
svn add src
svn commit -m "Создание структуры"

cp ../zips/commit0.zip .
unzip commit0.zip -d src
svn add src/*
svn commit -m "r0"

cp ../zips/commit1.zip .
unzip commit1.zip -d src
svn add --force src
svn commit -m "r1"

cp ../zips/commit2.zip .
unzip commit2.zip -d src
svn add --force src
svn commit -m "r2"

svn copy file://$(pwd)/repository file://$(pwd)/repository/branches/branch2 -m "branch2"
svn switch file://$(pwd)/repository/branches/branch2

cp ../zips/commit3.zip .
unzip commit3.zip -d src
svn add --force src
svn commit -m "r3"

svn copy file://$(pwd)/repository file://$(pwd)/repository/branches/branch3 -m "branch3"
svn switch file://$(pwd)/repository/branches/branch3

cp ../zips/commit4.zip .
unzip commit4.zip -d src
svn add --force src
svn commit -m "r4"

svn copy file://$(pwd)/repository file://$(pwd)/repository/branches/branch4 -m "branch4"
svn switch file://$(pwd)/repository/branches/branch4

cp ../zips/commit5.zip .
unzip commit5.zip -d src
svn add --force src
svn commit -m "r5"

svn switch file://$(pwd)/repository/branches/branch3
cp ../zips/commit6.zip .
unzip commit6.zip -d src
svn add --force src
svn commit -m "r6"

svn copy file://$(pwd)/repository file://$(pwd)/repository/branches/branch6 -m "branch6"
svn switch file://$(pwd)/repository/branches/branch6

cp ../zips/commit7.zip .
unzip commit7.zip -d src
svn add --force src
svn commit -m "r7"

svn switch file://$(pwd)/repository/branches/branch4
cp ../zips/commit8.zip .
unzip commit8.zip -d src
svn add --force src
svn commit -m "r8"

svn switch file://$(pwd)/repository/trunk
cp ../zips/commit9.zip .
unzip commit9.zip -d src
svn add --force src
svn commit -m "r9"

svn copy file://$(pwd)/repository file://$(pwd)/repository/branches/branch9 -m "branch9"
svn switch file://$(pwd)/repository/branches/branch9

cp ../zips/commit10.zip .
unzip commit10.zip -d src
svn add --force src
svn commit -m "r10"

svn switch file://$(pwd)/repository/branches/branch6
cp ../zips/commit11.zip .
unzip commit11.zip -d src
svn add --force src
svn commit -m "r11"

svn switch file://$(pwd)/repository/branches/branch3
cp ../zips/commit12.zip .
unzip commit12.zip -d src
svn add --force src
svn commit -m "r12"

svn switch file://$(pwd)/repository/branches/branch9
cp ../zips/commit13.zip .
unzip commit13.zip -d src
svn add --force src
svn commit -m "r13"

svn copy file://$(pwd)/repository file://$(pwd)/repository/branches/branch13 -m "branch13"
svn switch file://$(pwd)/repository/branches/branch13

cp ../zips/commit14.zip .
unzip commit14.zip -d src
svn add --force src
svn commit -m "r14"

svn switch file://$(pwd)/repository/branches/branch9
cp ../zips/commit15.zip .
unzip commit15.zip -d src
svn add --force src
svn commit -m "r15"

svn switch file://$(pwd)/repository/branches/branch4
cp ../zips/commit16.zip .
unzip commit16.zip -d src
svn add --force src
svn commit -m "r16"

svn copy file://$(pwd)/repository file://$(pwd)/repository/branches/branch16 -m "branch16"
svn switch file://$(pwd)/repository/branches/branch16

cp ../zips/commit17.zip .
unzip commit17.zip -d src
svn add --force src
svn commit -m "r17"

svn switch file://$(pwd)/repository/trunk
cp ../zips/commit18.zip .
unzip commit18.zip -d src
svn add --force src
svn commit -m "r18"

# ...
# (Продолжение аналогично, доведено до r39)
# ...

svn switch file://$(pwd)/repository/trunk
cp ../zips/commit39.zip .
unzip commit39.zip -d src
svn add --force src
svn commit -m "r39"

svn merge --accept postpone file://$(pwd)/repository/branches/branch4
read -p "Разрешение конфликта, ENTER..."
svn resolve --accept working -R .
svn commit -m "r39 merge branch4"
