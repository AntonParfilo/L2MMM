<?php

function read_database (){
	$db_adress = "localhost";
	$db_name = '******';
	$db_login = '*******';
	$db_pass = '******'; 
	$mysqli = new mysqli($db_adress, $db_login, $db_pass, $db_name) or die("Не могу подлкючиться к базе данных");
	$mysqli->query("SET NAMES 'utf8'");
	$date = explode(".", date("d.m.Y"));
	$naw_date = $date['2']."-".$date['1']."-".$date['0'];


	if(isset($_GET['key'])){
		if($_GET['key'] == "getServer"){
			$ch = $_GET['chronicle'];
			$rates = $_GET['rates'];
			$date = $_GET['date'];
			if($ch == "ALL" && $rates == "ALL" && $date == "AFTER"){
				$result = $mysqli->query("SELECT * FROM `anons` WHERE `date`>='$naw_date' ORDER BY `date` ASC");
				$res = array();
				$res_vip = array();
				foreach($result as $value){
					if($value['vip'] == 1){
						$res_vip[] = $value;
					}else $res[] = $value;
				}
				$res = array_merge($res_vip, $res);
				
				for($i=0;$i<count($res);$i++){
					$id = $res[$i]['id'];
					$res[$i]['rating_count'] = 0;
					$result_rating = $mysqli->query("SELECT * FROM `server_rating` WHERE `server_id` = '$id'");
					if($result_rating->num_rows > 0){
						if($result_rating->num_rows > 2){
							$all_stars = 0;
							foreach($result_rating as $value){
								$all_stars += $value['rating_value'];
							}
							$all_stars = $all_stars/$result_rating->num_rows;
							$all_stars = round($all_stars);
						}
						else $all_stars = 0;
						$res[$i]['stars'] = $all_stars;
						$res[$i]['rating_count'] = $result_rating->num_rows;
					}
				}

				echo json_encode($res);
			}
			if($ch == "ALL" && $rates == "ALL" && $date == "BEFORE"){
				$result = $mysqli->query("SELECT * FROM `anons` WHERE `date`<='$naw_date' ORDER BY `date` DESC");
				$res = array();
				$res_vip = array();
				foreach($result as $value){
					if($value['vip'] == 1){
						$res_vip[] = $value;
					}else $res[] = $value;
				}
				$res = array_merge($res_vip, $res);
				for($i=0;$i<count($res);$i++){
					$id = $res[$i]['id'];
					$res[$i]['rating_count'] = 0;
					$result_rating = $mysqli->query("SELECT * FROM `server_rating` WHERE `server_id` = '$id'");
					if($result_rating->num_rows > 0){
						if($result_rating->num_rows > 2){
							$all_stars = 0;
							foreach($result_rating as $value){
								$all_stars += $value['rating_value'];
							}
							$all_stars = $all_stars/$result_rating->num_rows;
							$all_stars = round($all_stars);
						}
						else $all_stars = 0;
						$res[$i]['stars'] = $all_stars;
						$res[$i]['rating_count'] = $result_rating->num_rows;
					}
				}	

				echo json_encode($res);
			}
			if($ch == "ALL" && $rates !== "ALL" && $date == "BEFORE"){
				$result = $mysqli->query("SELECT * FROM `anons` WHERE `date`<='$naw_date' AND `rates`='$rates' ORDER BY `date` DESC");
				$res = array();
				$res_vip = array();
				foreach($result as $value){
					if($value['vip'] == 1){
						$res_vip[] = $value;
					}else $res[] = $value;
				}
				$res = array_merge($res_vip, $res);
				for($i=0;$i<count($res);$i++){
					$id = $res[$i]['id'];
					$res[$i]['rating_count'] = 0;
					$result_rating = $mysqli->query("SELECT * FROM `server_rating` WHERE `server_id` = '$id'");
					if($result_rating->num_rows > 0){
						if($result_rating->num_rows > 2){
							$all_stars = 0;
							foreach($result_rating as $value){
								$all_stars += $value['rating_value'];
							}
							$all_stars = $all_stars/$result_rating->num_rows;
							$all_stars = round($all_stars);
						}
						else $all_stars = 0;
						$res[$i]['stars'] = $all_stars;
						$res[$i]['rating_count'] = $result_rating->num_rows;
					}
				}	
				echo json_encode($res);
			}
			if($ch !== "ALL" && $rates !== "ALL" && $date == "BEFORE"){
				$result = $mysqli->query("SELECT * FROM `anons` WHERE `date`<='$naw_date' AND `rates`='$rates' AND `chronicle`='$ch' ORDER BY `date` DESC");
				$res = array();
				$res_vip = array();
				foreach($result as $value){
					if($value['vip'] == 1){
						$res_vip[] = $value;
					}else $res[] = $value;
				}
				$res = array_merge($res_vip, $res);
				for($i=0;$i<count($res);$i++){
					$id = $res[$i]['id'];
					$res[$i]['rating_count'] = 0;
					$result_rating = $mysqli->query("SELECT * FROM `server_rating` WHERE `server_id` = '$id'");
					if($result_rating->num_rows > 0){
						if($result_rating->num_rows > 2){
							$all_stars = 0;
							foreach($result_rating as $value){
								$all_stars += $value['rating_value'];
							}
							$all_stars = $all_stars/$result_rating->num_rows;
							$all_stars = round($all_stars);
						}
						else $all_stars = 0;
						$res[$i]['stars'] = $all_stars;
						$res[$i]['rating_count'] = $result_rating->num_rows;
					}
				}	
				echo json_encode($res);
			}
			if($ch !== "ALL" && $rates == "ALL" && $date == "AFTER"){
				$result = $mysqli->query("SELECT * FROM `anons` WHERE `date`>='$naw_date' AND `chronicle`='$ch' ORDER BY `date` ASC");
				$res = array();
				$res_vip = array();
				foreach($result as $value){
					if($value['vip'] == 1){
						$res_vip[] = $value;
					}else $res[] = $value;
				}
				$res = array_merge($res_vip, $res);
				for($i=0;$i<count($res);$i++){
					$id = $res[$i]['id'];
					$res[$i]['rating_count'] = 0;
					$result_rating = $mysqli->query("SELECT * FROM `server_rating` WHERE `server_id` = '$id'");
					if($result_rating->num_rows > 0){
						if($result_rating->num_rows > 2){
							$all_stars = 0;
							foreach($result_rating as $value){
								$all_stars += $value['rating_value'];
							}
							$all_stars = $all_stars/$result_rating->num_rows;
							$all_stars = round($all_stars);
						}
						else $all_stars = 0;
						$res[$i]['stars'] = $all_stars;
						$res[$i]['rating_count'] = $result_rating->num_rows;
					}
				}	
				echo json_encode($res);
			}
			if($ch !== "ALL" && $rates !== "ALL" && $date == "AFTER"){
				$result = $mysqli->query("SELECT * FROM `anons` WHERE `date`>='$naw_date' AND `rates`='$rates' AND `chronicle`='$ch' ORDER BY `date` ASC");
				$res = array();
				$res_vip = array();
				foreach($result as $value){
					if($value['vip'] == 1){
						$res_vip[] = $value;
					}else $res[] = $value;
				}
				$res = array_merge($res_vip, $res);
				for($i=0;$i<count($res);$i++){
					$id = $res[$i]['id'];
					$res[$i]['rating_count'] = 0;
					$result_rating = $mysqli->query("SELECT * FROM `server_rating` WHERE `server_id` = '$id'");
					if($result_rating->num_rows > 0){
						if($result_rating->num_rows > 2){
							$all_stars = 0;
							foreach($result_rating as $value){
								$all_stars += $value['rating_value'];
							}
							$all_stars = $all_stars/$result_rating->num_rows;
							$all_stars = round($all_stars);
						}
						else $all_stars = 0;
						$res[$i]['stars'] = $all_stars;
						$res[$i]['rating_count'] = $result_rating->num_rows;
					}
				}	
				echo json_encode($res);
			}
			if($ch !== "ALL" && $rates == "ALL" && $date == "BEFORE"){
				$result = $mysqli->query("SELECT * FROM `anons` WHERE `date`<='$naw_date' AND `chronicle`='$ch' ORDER BY `date` DESC");
				$res = array();
				$res_vip = array();
				foreach($result as $value){
					if($value['vip'] == 1){
						$res_vip[] = $value;
					}else $res[] = $value;
				}
				$res = array_merge($res_vip, $res);
				for($i=0;$i<count($res);$i++){
					$id = $res[$i]['id'];
					$res[$i]['rating_count'] = 0;
					$result_rating = $mysqli->query("SELECT * FROM `server_rating` WHERE `server_id` = '$id'");
					if($result_rating->num_rows > 0){
						if($result_rating->num_rows > 2){
							$all_stars = 0;
							foreach($result_rating as $value){
								$all_stars += $value['rating_value'];
							}
							$all_stars = $all_stars/$result_rating->num_rows;
							$all_stars = round($all_stars);
						}
						else $all_stars = 0;
						$res[$i]['stars'] = $all_stars;
						$res[$i]['rating_count'] = $result_rating->num_rows;
					}
				}	
				echo json_encode($res);
			}
			if($ch == "ALL" && $rates !== "ALL" && $date == "AFTER"){
				$result = $mysqli->query("SELECT * FROM `anons` WHERE `date`>='$naw_date' AND `rates`='$rates' ORDER BY `date` ASC");
				$res = array();
				$res_vip = array();
				foreach($result as $value){
					if($value['vip'] == 1){
						$res_vip[] = $value;
					}else $res[] = $value;
				}
				$res = array_merge($res_vip, $res);
				for($i=0;$i<count($res);$i++){
					$id = $res[$i]['id'];
					$res[$i]['rating_count'] = 0;
					$result_rating = $mysqli->query("SELECT * FROM `server_rating` WHERE `server_id` = '$id'");
					if($result_rating->num_rows > 0){
						if($result_rating->num_rows > 2){
							$all_stars = 0;
							foreach($result_rating as $value){
								$all_stars += $value['rating_value'];
							}
							$all_stars = $all_stars/$result_rating->num_rows;
							$all_stars = round($all_stars);
						}
						else $all_stars = 0;
						$res[$i]['stars'] = $all_stars;
						$res[$i]['rating_count'] = $result_rating->num_rows;
					}
				}	
				echo json_encode($res);
			}
			if($ch == "ALL" && $rates !== "ALL" && $date == "BEFORE"){
				$result = $mysqli->query("SELECT * FROM `anons` WHERE `date`<='$naw_date' AND `rates`='$rates' ORDER BY `date` DESC");
				$res = array();
				$res_vip = array();
				foreach($result as $value){
					if($value['vip'] == 1){
						$res_vip[] = $value;
					}else $res[] = $value;
				}
				$res = array_merge($res_vip, $res);
				for($i=0;$i<count($res);$i++){
					$id = $res[$i]['id'];
					$res[$i]['rating_count'] = 0;
					$result_rating = $mysqli->query("SELECT * FROM `server_rating` WHERE `server_id` = '$id'");
					if($result_rating->num_rows > 0){
						if($result_rating->num_rows > 2){
							$all_stars = 0;
							foreach($result_rating as $value){
								$all_stars += $value['rating_value'];
							}
							$all_stars = $all_stars/$result_rating->num_rows;
							$all_stars = round($all_stars);
						}
						else $all_stars = 0;
						$res[$i]['stars'] = $all_stars;
						$res[$i]['rating_count'] = $result_rating->num_rows;
					}
				}	
				echo json_encode($res);
			}
			if($ch !== "ALL" && $rates !== "ALL" && $date == "AFTER"){
				$result = $mysqli->query("SELECT * FROM `anons` WHERE `date`>='$naw_date' AND `rates`='$rates' AND `chronicle`='$ch' ORDER BY `date` ASC");
				$res = array();
				$res_vip = array();
				foreach($result as $value){
					if($value['vip'] == 1){
						$res_vip[] = $value;
					}else $res[] = $value;
				}
				$res = array_merge($res_vip, $res);
				for($i=0;$i<count($res);$i++){
					$id = $res[$i]['id'];
					$res[$i]['rating_count'] = 0;
					$result_rating = $mysqli->query("SELECT * FROM `server_rating` WHERE `server_id` = '$id'");
					if($result_rating->num_rows > 0){
						if($result_rating->num_rows > 2){
							$all_stars = 0;
							foreach($result_rating as $value){
								$all_stars += $value['rating_value'];
							}
							$all_stars = $all_stars/$result_rating->num_rows;
							$all_stars = round($all_stars);
						}
						else $all_stars = 0;
						$res[$i]['stars'] = $all_stars;
						$res[$i]['rating_count'] = $result_rating->num_rows;
					}
				}	
				echo json_encode($res);
			}




			
			

		}


		









		if($_GET['key'] == "getAllChronicle"){
			$result = $mysqli->query("SELECT chronicle FROM `anons`");
			$res = array();
			foreach($result as $value){
				$res[] = $value['chronicle'];
			}	

				$res = array_unique($res);

				$res1 = array();
				$res1[]['chronicle'] .= "Все";
				foreach($res as $value){
					$res1[]['chronicle'] .= $value;
				}
				echo json_encode($res1);
		}


		if($_GET['key'] == "getAllRates"){
			$result = $mysqli->query("SELECT rates FROM `anons` ORDER BY `rates` ASC");
			$res = array();
			foreach($result as $value){
				$res[] = $value['rates'];
			}	
				$res = array_unique($res);	
				$res1 = array();
				foreach($res as $value){
					$res1[]['rates'] .= $value;
				}
				asort($res1);
				$res2 = array();
				$all_rates['rates'] = "Все";
				array_unshift($res1, $all_rates);
				foreach($res1 as $value){
					$res2[] = $value;
				}

				echo json_encode($res2);
		}

		if($_GET['key'] == "viewServer"){
			$server_id = $_GET['server_id'];
			$result = $mysqli->query("SELECT * FROM `anons` WHERE `id` = '$server_id'");
			$result_rating = $mysqli->query("SELECT * FROM `server_rating` WHERE `server_id` = '$server_id'");
			if($result_rating->num_rows > 2){
				$all_stars = 0;
				foreach($result_rating as $value){
					$all_stars += $value['rating_value'];
				}
				$all_stars = $all_stars/$result_rating->num_rows;
				$all_stars = round($all_stars);
			}
			else $all_stars = 0;
			$res1[] = $result->fetch_assoc();
			$res1[0]['stars'] = strval($all_stars);
			echo json_encode($res1);
			
		}
		if($_GET['key'] == "setRating"){
			$server_id = $_GET['server_id'];
			$user_email = $_GET['user_email'];
			$rating_value = $_GET['rating_value'];
			$result = $mysqli->query("INSERT INTO `server_rating` (`id`, `server_id`, `user_email`, `rating_value`) VALUES (NULL, '$server_id', '$user_email', '$rating_value')");
			if($result) $res = "TRUE";
			else $res = "FALSE";
			echo json_encode($res);
		}

		if($_GET['key'] == "getComments"){
			$server_id = $_GET['server_id'];
			$result = $mysqli->query("SELECT * FROM `comments` WHERE `server_id` = '$server_id' ORDER BY `id` DESC");
			$res = array();
			if($result->num_rows > 0){
				foreach($result as $value){
				$res[] = $value;
				}
			} else $res[] = "FALSE";
			
			echo json_encode($res);
			
		}

		if($_GET['key'] == "setComments"){
			$server_id = $_GET['server_id'];
			$comment = $_GET['comment'];
			$user_name = $_GET['user_name'];
			$user_image = $_GET['user_image'];
			$date = date("Y-m-d h:i:s");
			$result_arm = $mysqli->query("SELECT * FROM `comments` WHERE `server_id` = '$server_id' and `user_name` = '$user_name' ORDER BY `id` DESC");
			if($result_arm->num_rows > 0){
				$arm_date = $result_arm->fetch_assoc();
				$arm_date = $arm_date['date'];
				$d = DateTime::createFromFormat('Y-m-d h:i:s', $arm_date);
				$naw_d = date("Y-m-d h:i:s");
				$dn = DateTime::createFromFormat('Y-m-d h:i:s', $naw_d);
				$rasn = $dn->getTimestamp() - $d->getTimestamp();
				if($rasn < 180){
					echo json_encode("Armor");
				}
				else{
					$result = $mysqli->query("INSERT INTO `comments` (`id`, `server_id`, `date`, `comment`, `user_name`, `user_image`) VALUES (NULL, '$server_id', '$date', '$comment', '$user_name', '$user_image')");
					if($result) $res = "TRUE";
					else $res = "FALSE";
					echo json_encode($res);
				}
			}
			else{
				$result = $mysqli->query("INSERT INTO `comments` (`id`, `server_id`, `date`, `comment`, `user_name`, `user_image`) VALUES (NULL, '$server_id', '$date', '$comment', '$user_name', '$user_image')");
					if($result) $res = "TRUE";
					else $res = "FALSE";
					echo json_encode($res);
			}
			
			
		}
		if($_GET['key'] == "addServer"){
			$server_address = $_GET['server_address'];
			$rates = $_GET['rates'];
			$chronicle = $_GET['chronicle'];
			$date = $_GET['date'];
			$user_email = $_GET['user_email'];
			$result_arm = $mysqli->query("SELECT * FROM `anons` WHERE `server_adress` = '$server_address'");
			if($result_arm->num_rows < 2){
				$result = $mysqli->query("INSERT INTO `anons` (`id`, `server_adress`, `chronicle`, `rates`, `date`, `user_name`, `time`, `vip`, `start`, `rating_count`, `client_mail`, `stars`) VALUES (NULL, '$server_address', '$chronicle', '$rates', '$date', '', '', '', '', '', '$user_email', '')");
				if($result) $res = "TRUE";
				else $res = "FALSE";
				echo json_encode($res);
			} else echo json_encode("FALSE");
			require_once($_SERVER['DOCUMENT_ROOT']."/libs/PHPMailer/PHPMailerAutoload.php");

			$mail = new PHPMailer;
			$mail->CharSet = 'UTF-8';
// Настройки SMTP
			$mail->isSMTP();                        
			$mail->SMTPAuth = true; 
			$mail->SMTPDebug = 0;                   
			$mail->isHTML(true);
			$mail->ContentType = 'text/plain';
			$mail->Host = 'ssl://smtp.gmail.com';
			$mail->Port = 465;
			$mail->Username = '*******';
			$mail->Password = '*******';
			$subject = "Новый сервер - ".$server_address;
// От кого
			$mail->setFrom('l2to4ka.mail@gmail.com', 'L2M');		
// Кому
			$mail->addAddress('antoxa106@gmail.com', 'L2M');
// Тема письма
			$mail->Subject = $subject;
// Тело письма
			$body = "<b>Добавлен новый сервер</b><br>\r\n";
			$body .= "<b>Адрес сервера - ".$server_address."</b><br>\r\n";
			$body .= "<b>Хроники - ".$chronicle."</b><br>\r\n";
			$body .= "<b>Рейты - ".$rates."</b><br>\r\n";
			$body .= "<b>Дата - ".$date."</b><br>\r\n";
			$body .= "<b>Почта - ".$user_email."</b><br>\r\n";
			$mail->msgHTML($body);
			$mail->send();
		}
		if($_GET['key'] == "feedback"){
			$user_name = $_GET['user_name'];
			$user_email = $_GET['user_email'];
			$message = $_GET['message'];
			$date = date("Y-m-d h:i:s");
			$result_arm = $mysqli->query("SELECT * FROM `feedback` WHERE `user_name` = '$user_name' and `user_email` = '$user_email' ORDER BY `id` DESC");
			if($result_arm->num_rows > 0){
				$arm_date = $result_arm->fetch_assoc();
				$arm_date = $arm_date['date'];
				$d = DateTime::createFromFormat('Y-m-d h:i:s', $arm_date);
				$naw_d = date("Y-m-d h:i:s");
				$dn = DateTime::createFromFormat('Y-m-d h:i:s', $naw_d);
				$rasn = $dn->getTimestamp() - $d->getTimestamp();
				if($rasn < 180){
					echo json_encode("Armor");
				}
				else{
					$result = $mysqli->query("INSERT INTO `feedback` (`id`, `user_name`, `user_email`, `message`, `date`) VALUES (NULL, '$user_name', '$user_email', '$message', '$date')");
					require_once($_SERVER['DOCUMENT_ROOT']."/libs/PHPMailer/PHPMailerAutoload.php");
					$mail = new PHPMailer;
					$mail->CharSet = 'UTF-8';
// Настройки SMTP
					$mail->isSMTP();                        
					$mail->SMTPAuth = true; 
					$mail->SMTPDebug = 0;                   
					$mail->isHTML(true);
					$mail->ContentType = 'text/plain';
					$mail->Host = 'ssl://smtp.gmail.com';
					$mail->Port = 465;
					$mail->Username = '*******';
					$mail->Password = '*******';
					$subject = "Обратная связь";
// От кого
					$mail->setFrom('l2to4ka.mail@gmail.com', 'L2M');		
// Кому
					$mail->addAddress('antoxa106@gmail.com', 'L2M');
// Тема письма
					$mail->Subject = $subject;
// Тело письма
					$body = "<b>Обратная связь приложения l2M</b><br>\r\n";
					$body .= "<b>Имя пользователя - ".$user_name."</b><br>\r\n";
					$body .= "<b>Почтовый ящик - ".$user_email."</b><br>\r\n";
					$body .= "<b>Текст сообщения:</b><br>\r\n";
					$body .= "<b>".$message."</b><br>\r\n";
					$mail->msgHTML($body);
					$mail->send();
					echo json_encode("TRUE");
				}
			}
			else{
				$result = $mysqli->query("INSERT INTO `feedback` (`id`, `user_name`, `user_email`, `message`, `date`) VALUES (NULL, '$user_name', '$user_email', '$message', '$date')");
					require_once($_SERVER['DOCUMENT_ROOT']."/libs/PHPMailer/PHPMailerAutoload.php");
			$mail = new PHPMailer;
			$mail->CharSet = 'UTF-8';
// Настройки SMTP
			$mail->isSMTP();                        
			$mail->SMTPAuth = true; 
			$mail->SMTPDebug = 0;                   
			$mail->isHTML(true);
			$mail->ContentType = 'text/plain';
			$mail->Host = 'ssl://smtp.gmail.com';
			$mail->Port = 465;
			$mail->Username = '*******';
			$mail->Password = '*******';
			$subject = "Обратная связь";
// От кого
			$mail->setFrom('l2to4ka.mail@gmail.com', 'L2M');		
// Кому
			$mail->addAddress('antoxa106@gmail.com', 'L2M');
// Тема письма
			$mail->Subject = $subject;
// Тело письма
			$body = "<b>Обратная связь приложения l2M</b><br>\r\n";
			$body .= "<b>Имя пользователя - ".$user_name."</b><br>\r\n";
			$body .= "<b>Почтовый ящик - ".$user_email."</b><br>\r\n";
			$body .= "<b>Текст сообщения:</b><br>\r\n";
			$body .= "<b>".$message."</b><br>\r\n";
			$mail->msgHTML($body);
			$mail->send();
			echo json_encode("TRUE");
			}


			
		}

		if($_GET['key'] == "sendToken"){
			$user_token = $_GET['token'];

			$result = $mysqli->query("SELECT * FROM `users_token` WHERE `user_token` = '$user_token'");
			if($result->num_rows == 0){
				$result = $mysqli->query("INSERT INTO `users_token` (`id`, `user_token`, `active`) VALUES (NULL, '$user_token', '1')");
				if($result) $res = "TRUE";
				else $res = "FALSE";
			}
			else $res = "FALSE";
			echo json_encode($res);
			
		}
	}
}

read_database();





?>