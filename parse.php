<?php
	$source  = file_get_contents($argv[1]);

	unlink($argv[1]);

	$preprocessed="";
	foreach(explode("\n", $source) as $line){
		if(substr($line, 0, 1) === "#"){
			$line=substr($line, 1);
		}
		$old_hex = "";
		while(strlen($line) > 74){
			$current_hex=trim(substr($line, 0, 5));
			$current_hex_length=strlen($current_hex);
			$current_dec=hexdec($current_hex);
			$next_dec=$current_dec+16;
			$next_hex=dechex($next_dec);
			if($current_hex_length > 4){
				$next = sprintf('%05x', $next_dec);
			} else {
				$next = sprintf('%04x', $next_dec);
			}
			$next.="  ";
			$this_line = substr($line, 0, strpos($line, $next));
				 $line = substr($line, strpos($line, $next));
			$preprocessed.=$this_line."\n";
		}
		$preprocessed.=$line."\n";
	}

	$lines = explode("\n", trim($preprocessed));

	$hex_content = "";
	for ($i = 0; $i < count($lines); $i++) {
		$line = $lines[$i];
		if(empty(trim($line))){
			break;
		}
		if($i==(count($lines)-1)){
			$hex_line=str_replace(" ", "", trim(substr($line, 5, ((strlen($line)-5)/4)*3)));
		} else {
			$hex_line=str_replace(" ", "", trim(substr($line, 5, 48)));
		}
		$hex_content.=$hex_line;
	}

	$content=hex2bin($hex_content);

	$content = substr($content, 0, strrpos($content, '}')+1);
	list($usec, $sec) = explode(" ",microtime());
	$timestamp = $sec.$usec*1000000;

	$base_path="/home/developer/private-backup/development/christian.zudeick-sandbox/trunk/node/galacticclash/";

    $connection = new mysqli('<host>', '<user>', '<password>', '<database>');

    if($connection->connect_error){
        die("Connection failed: ".$connection->connect_error);
    }

    $statement = $connection->prepare("INSERT INTO gc_import(data) VALUES (?)");
    $statement->bind_param("s", $data);
    $data      = $content;
    $statement->execute();
    $statement->close();
    $connection->close();
?>
