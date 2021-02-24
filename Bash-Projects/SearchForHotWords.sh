#! /bin/bash

# This is a simple program that search files and folders recursively
# to find hot words sent to the get_File() method.

# This Function checks files for hot words and copies them to a folder.
get_File() {
	local str="$1"
	local fil="$2"

	# Check files for hot words.
	check=$(grep -ni "$str" "$fil")
	if [ -z "$check" ]; then
		echo "### EMPTY! ###"
	else
		echo "FOUND!"
		local name_file=$(basename "$fil")

		# Copy file to hot-Folder folder.
		cp "$fil" hot-Folder/"$name_file"
		echo " " >> hot-Folder/"$name_file"
		echo "*----------------------------*" >> hot-Folder/"$name_file"
		echo "$check" >> hot-Folder/"$name_file"
	fi
}

############################### Main Program ###########################
rm -r /hot-folder
mkdir ./hot-Folder
for i in $(find . -type d) ;
do
# avoid current dir.
if [ "$i" != "./hot-folder" ]; then
	echo "*************** THE FOLDER IS $i *****************"
	# loop through to find hot files.
	for myfile in $i/* ;
	do
		# check for files and dir.
		if [ -f "$myfile" ]; then
			get_File "spo" "$myfile" 
		fi
	done
	echo "----------------------------------------"
fi
done
################################ End ###################################
