#!/bin/bash
rm -f /<wherever-you-like>/galacticclash/queue/*.log

project_root="/<wherever-you-like>/galacticclash"
base_name="$project_root/queue"
file_name="$base_name/response.$(date +%s%3N).log"
uncompressed=false
end=null
while read line;do
#	echo "#$line#"
	if [ "$uncompressed" = true ] && ([[ $line =~ "$end  " ]] || [[ $line == "" ]]); then
		echo "#$line#" >> $file_name
		uncompressed=false
		end=null
		old_file_name=$file_name
		file_name="$base_name/response.$(date +%s%3N).log"
		if [ -f $old_file_name ]; then
			php -f $project_root/parse.php $old_file_name $1
		fi
	else
		if [[ $line == *"ncompressed entity body"* ]]; then
			BYTES=`echo $line| cut -d'(' -f 2| cut -d " " -f1`
			uncompressed=true
			end=$(printf "%04x\n" $((($BYTES / 16)*16)))
		else
			if [ "$uncompressed" = true ]; then
				echo "#$line#" >> $file_name
			fi
		fi
	fi
done
