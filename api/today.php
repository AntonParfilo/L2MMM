<?php

    $db_adress = "localhost";
    $db_name = '*******';
    $db_login = '*******';
    $db_pass = '*******'; 
    $mysqli = new mysqli($db_adress, $db_login, $db_pass, $db_name) or die("Не могу подлкючиться к базе данных");
    $mysqli->query("SET NAMES 'utf8'");
    $date = explode(".", date("d.m.Y"));
    $naw_date = $date['2']."-".$date['1']."-".$date['0'];

    $result = $mysqli->query("SELECT * FROM `anons` WHERE `date`='$naw_date'");
    if($result->num_rows > 0){

        $result_users = $mysqli->query("SELECT `user_token` FROM `users_token` WHERE `active`='1'");
        $users = array();
        foreach($result_users as $value){
            $users[] = $value['user_token'];
        }

        $num = $result->num_rows;
        $url = "https://fcm.googleapis.com/fcm/send";
        $token = "/topics/news";
        $serverKey = '*******';
        if($num == 1) $title = "Сегодня открывается ".$num." новый сервер.";
        else if($num > 1 and $num < 5) $title = "Сегодня открываются ".$num." новых сервера.";
        else if($num > 4 ) $title = "Сегодня открывается ".$num." новых серверов.";
        $body = "Зацени новые проекты. Старт сегодня!";
        $data = array('message' => $body, 'title' => $title);
        $arrayToSend = array('to' => $token, 'data' => $data,'priority'=>'high');
        $json = json_encode($arrayToSend);
        $headers = array();
        $headers[] = 'Content-Type: application/json';
        $headers[] = 'Authorization: key='. $serverKey;
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST,"POST");
        curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
        curl_setopt($ch, CURLOPT_HTTPHEADER,$headers);
        //Send the request
        $response = curl_exec($ch);
        //Close request
        if ($response === FALSE) {
            die('FCM Send Error: ' . curl_error($ch));
        }
        curl_close($ch);
    }
    
    
    
    
?>
