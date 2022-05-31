<?php
	include( 'simple_html_dom.php' );
	$html = new simple_html_dom(  );
	$html->load_file( 'https://l2op.ru' );
	$content = $html->find(".left", 0);
	
	foreach($content->find(".server") as $value){
		$name = $value->find("a", 0);
		$name = $name->plaintext;
	
		$rates = $value->find(".rates", 0);
		$rates = $rates->plaintext;
		$rates = substr($rates, 1);
		
		$ch = $value->find(".chronicle", 0);
		$ch = $ch->plaintext;
		
		$date = $value->find(".date", 0);
		$date = $date->plaintext;
		$date = substr($date, 0, -9);
		if($date == "сегодня" || $date == "завтра"){
			$date = 0;
		}
		else{
			$d = substr($date, 0, 2);
			$m = substr($date, 3, 2);
			$y = substr($date, 6, 4);
			$date = $y."-".$m."-".$d;
		}
		

		if($ch == "High Five") $ch = "High-Five";
		if($ch == "Epilogue") $ch = "Gracia Epilogue";

		
		if($rates !== "VE" && $rates !== "VR" && $date !== 0){
			$servers[]= array(
				"id" => "",
				"server_adress" => $name,
				"chronicle" => $ch,
				"rates" => $rates,
				"date" => $date,
				"user_name" => "",
				"time" => "",
				"vip" => "0",
				"start" => "",
				"rating_count" => "",
				"client_mail" => "l2to4ka@gmail.com",
				"stars" => "0"
			);
		}
		

	}
	

	$db_adress = "localhost";
	$db_name = '*******';
	$db_login = '*******';
	$db_pass = '*******'; 
	$mysqli = new mysqli($db_adress, $db_login, $db_pass, $db_name) or die("Не могу подлкючиться к базе данных");
	$mysqli->query("SET NAMES 'utf8'");


	foreach ($servers as $value) {
		$server_name = $value['server_adress'];
		$server_ch = $value['chronicle'];
		$server_rates = $value['rates'];
		$server_date = $value['date'];
		$result_arm = $mysqli->query("SELECT * FROM `anons` WHERE `server_adress` = '$server_name' and `date` = '$server_date'");
		
		if($result_arm->num_rows == 0){
			$result = $mysqli->query("INSERT INTO `anons` (`id`, `server_adress`, `chronicle`, `rates`, `date`, `user_name`, `time`, `vip`, `start`, `rating_count`, `client_mail`, `stars`) VALUES (NULL, '$server_name', '$server_ch', '$server_rates', '$server_date', '', '', '', '', '', 'l2to4ka@gmail.com', '')");
			echo "Добавлен сервер - ".$server_name."</br>";
			
		}
		else{
			echo "Такой сервер уже есть в базе!</br>";
		}

	}


	

?>
