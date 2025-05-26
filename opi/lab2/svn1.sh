
rm -rf svn
mkdir svn
cd svn
svnadmin create rep
svn checkout file://$(pwd)/rep ./src
cd src

mkdir trunk branches tags
svn add trunk branches tags
svn commit -m "Init"
cd trunk

# r0 (user1)
unzip ~/lab2/zips/commit0.zip -d .
svn add --force .
svn commit -m "r0" --username=user1

# r1 (user1)
rm -rf *
unzip ~/lab2/zips/commit1.zip -d .
svn add --force .
svn commit -m "r1" --username=user1

# r2 (user1)
rm -rf *
unzip ~/lab2/zips/commit2.zip -d .
svn add --force .
svn delete qS25MwH9rf.31D
svn commit -m "r2" --username=user1

# branch2
svn copy ^/trunk ^/branches/branch2 -m "branch2"
svn switch ^/branches/branch2

# r3 (user2)
rm -rf *
unzip ~/lab2/zips/commit3.zip -d .
svn add --force .
svn commit -m "r3" --username=user2

# branch3
svn copy . ^/branches/branch3 -m "branch3"
svn switch ^/branches/branch3

# r4 (user2)
rm -rf *
unzip ~/lab2/zips/commit4.zip -d .
svn add --force .
svn commit -m "r4" --username=user2

# branch4
svn copy . ^/branches/branch4 -m "branch4"
svn switch ^/branches/branch4

# r5 (user2)
rm -rf *
unzip ~/lab2/zips/commit5.zip -d .
svn add --force .
svn commit -m "r5" --username=user2

# Возврат в branch3
svn switch ^/branches/branch3

# r6 (user1)
rm -rf *
unzip ~/lab2/zips/commit6.zip -d .
svn add --force .
svn commit -m "r6" --username=user1

# branch6
svn copy . ^/branches/branch6 -m "branch6"
svn switch ^/branches/branch6

# r7 (user1)
rm -rf *
unzip ~/lab2/zips/commit7.zip -d .
svn add --force .
svn delete qS25MwH9rf.31D
svn commit -m "r7" --username=user1

#  branch4
svn switch ^/branches/branch4

# r8 (user2)
rm -rf *
unzip ~/lab2/zips/commit8.zip -d .
svn add --force .
svn commit -m "r8" --username=user2

# Возврат в trunk (master)
svn switch ^/trunk

# r9 (user1)
rm -rf *
unzip ~/lab2/zips/commit9.zip -d .
svn add --force .
svn commit -m "r9" --username=user1

# branch9
svn copy ^/trunk ^/branches/branch9 -m "branch9"
svn switch ^/branches/branch9

# r10 (user2)
rm -rf *
unzip ~/lab2/zips/commit10.zip -d .
svn add --force .
svn delete UJA1Ey31Dq.Sz4
svn commit -m "r10" --username=user2

# Возврат в branch6
svn switch ^/branches/branch6

# r11 (user1)
rm -rf *
unzip ~/lab2/zips/commit11.zip -d .
svn add --force .
svn commit -m "r11" --username=user1

# Возврат в branch3
svn switch ^/branches/branch3

# r12 (user2)
rm -rf *
unzip ~/lab2/zips/commit12.zip -d .
svn add --force .
svn delete qS25MwH9rf.31D
svn commit -m "r12" --username=user2

#  branch9
svn switch ^/branches/branch9

# r13 (user2)
rm -rf *
unzip ~/lab2/zips/commit13.zip -d .
svn add --force .
svn commit -m "r13" --username=user2

# branch13
svn copy . ^/branches/branch13 -m "branch13"
svn switch ^/branches/branch13

# r14 (user2)
rm -rf *
unzip ~/lab2/zips/commit14.zip -d .
svn add --force .
svn delete qS25MwH9rf.31D
svn commit -m "r14" --username=user2

# Возврат в branch9
svn switch ^/branches/branch9

# r15 (user2)
rm -rf *
unzip ~/lab2/zips/commit15.zip -d .
svn add --force .
svn commit -m "r15" --username=user2

#  branch4
svn switch ^/branches/branch4

# r16 (user2)
rm -rf *
unzip ~/lab2/zips/commit16.zip -d .
svn add --force .
svn delete qS25MwH9rf.31D
svn commit -m "r16" --username=user2

# branch16
svn copy . ^/branches/branch16 -m "branch16"
svn switch ^/branches/branch16

# r17 (user1)
rm -rf *
unzip ~/lab2/zips/commit17.zip -d .
svn add --force .
svn commit -m "r17" --username=user1

# Возврат в trunk
svn switch ^/trunk

# r18 (user1)
rm -rf *
unzip ~/lab2/zips/commit18.zip -d .
svn add --force .
svn delete UJA1Ey31Dq.Sz4
svn commit -m "r18" --username=user1

#  branch3
svn switch ^/branches/branch3

# r19 (user2)
rm -rf *
unzip ~/lab2/zips/commit19.zip -d .
svn add --force .
svn commit -m "r19" --username=user2

# branch19
svn copy . ^/branches/branch19 -m "branch19"
svn switch ^/branches/branch19

# r20 (user2)
rm -rf *
unzip ~/lab2/zips/commit20.zip -d .
svn add --force .
svn delete qS25MwH9rf.31D
svn commit -m "r20" --username=user2

# Возврат в branch3
svn switch ^/branches/branch3

# r21 (user2)
rm -rf *
unzip ~/lab2/zips/commit21.zip -d .
svn add --force .
svn delete qS2 5MwH9rf.31D
svn commit -m "r21" --username=user2

#  branch9
svn switch ^/branches/branch9

# r22 (user2)
rm -rf *
unzip ~/lab2/zips/commit22.zip -d .
svn add --force .
svn delete qS25MwH9rf.31D
svn commit -m "r22" --username=user2

# branch22
svn copy . ^/branches/branch22 -m "branch22"
svn switch ^/branches/branch22

# r23 (user1)
rm -rf *
unzip ~/lab2/zips/commit23.zip -d .
svn add --force .
svn delete UJA1Ey31Dq.Sz4
svn commit -m "r23" --username=user1

# Возврат в trunk
svn switch ^/trunk

# r24 (user1)
rm -rf *
unzip ~/lab2/zips/commit24.zip -d .
svn add --force .
svn commit -m "r24" --username=user1

#  branch9
svn switch ^/branches/branch9

# r25 (user2)
rm -rf *
unzip ~/lab2/zips/commit25.zip -d .
svn add --force .
svn delete UJA1Ey31Dq.Sz4
svn commit -m "r25" --username=user2

# branch25
svn copy . ^/branches/branch25 -m "branch25"
svn switch ^/branches/branch25

# r26 (user2)
rm -rf *
unzip ~/lab2/zips/commit26.zip -d .
svn add --force .
svn commit -m "r26" --username=user2

#  branch16
svn switch ^/branches/branch16

# r27 (user1)
rm -rf *
unzip ~/lab2/zips/commit27.zip -d .
svn add --force .
svn commit -m "r27" --username=user1

#  branch6
svn switch ^/branches/branch6

# r28 (merge branch16)
svn merge ^/branches/branch16 --accept postpone
svn resolve --accept working qS25MwH9rf.31D
rm -rf *
unzip ~/lab2/zips/commit28.zip -d .
svn add --force .
svn delete qS25MwH9rf.31D
svn commit -m "r28" --username=user1

#  branch19
svn switch ^/branches/branch19

# r29 (merge branch6)
svn merge ^/branches/branch6 --accept postpone
svn resolve --accept working -R .
rm -rf *
unzip ~/lab2/zips/commit29.zip -d .
svn add --force .
svn delete '*'
svn commit -m "r29" --username=user2

#  branch25
svn switch ^/branches/branch25

# r30 (merge branch19)
svn merge ^/branches/branch19 --accept postpone
svn resolve --accept working -R .
rm -rf *
unzip ~/lab2/zips/commit30.zip -d .
svn add --force .
svn commit -m "r30" --username=user2

#  branch9
svn switch ^/branches/branch9

# r31 (merge branch25)
svn merge ^/branches/branch25 --accept postpone
svn resolve --accept working -R .
rm -rf *
unzip ~/lab2/zips/commit31.zip -d .
svn add --force .
svn delete fKSf5GSS9S.p4m
svn commit -m "r31" --username=user2

#  branch4
svn switch ^/branches/branch4

# r32 (merge branch9)
svn merge ^/branches/branch9 --accept postpone
svn resolve --accept working -R .
rm -rf *
unzip ~/lab2/zips/commit32.zip -d .
svn add --force .
svn commit -m "r32" --username=user2

#  branch22
svn switch ^/branches/branch22

# r33 (merge branch4)
svn merge ^/branches/branch4 --accept postpone
svn resolve --accept working -R .
rm -rf *
unzip ~/lab2/zips/commit33.zip -d .
svn add --force .
svn commit -m "r33" --username=user1

#  branch3
svn switch ^/branches/branch3

# r34 (merge branch22)
svn merge ^/branches/branch22 --accept postpone
svn resolve --accept working -R .
rm -rf *
unzip ~/lab2/zips/commit34.zip -d .
svn add --force .
svn delete qS25MwH9rf.31D
svn delete '*'
svn commit -m "r34" --username=user2

# branch34
svn copy . ^/branches/branch34 -m "branch34"
svn switch ^/branches/branch34

# r35 (merge branch2)
svn merge ^/branches/branch2 --accept postpone
svn resolve --accept working -R .
rm -rf *
unzip ~/lab2/zips/commit35.zip -d .
svn add --force .
svn delete eEmYsy6XIB.N4G
svn delete 5lB1ugwswR.fLt
svn commit -m "r35" --username=user2

#  branch13
svn switch ^/branches/branch13

# r36 (merge branch34)
svn merge ^/branches/branch34 --accept postpone
svn resolve --accept working -R .
rm -rf *
unzip ~/lab2/zips/commit36.zip -d .
svn add --force .
svn commit -m "r36" --username=user2

# r37
rm -rf *
unzip ~/lab2/zips/commit37.zip -d .
svn add --force .
svn commit -m "r37" --username=user2

#  branch4
svn switch ^/branches/branch4

# r38 (merge branch34)
svn merge ^/branches/branch34 --accept postpone
svn resolve --accept working -R .
rm -rf *
unzip ~/lab2/zips/commit38.zip -d .
svn add --force .
svn commit -m "r38" --username=user2

# Возврат в trunk
svn switch ^/trunk

# r39 (merge branch4)
svn merge ^/branches/branch4 --accept postpone
svn resolve --accept working -R .
rm -rf *
unzip ~/lab2/zips/commit39.zip -d .
svn add --force .
svn commit -m "r39" --username=user1