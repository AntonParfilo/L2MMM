<?php


        $icon_server = "https://www.google.com/s2/favicons?domain=ketrawars.net"; // ИЗМЕНИТЬ ИКОНКУ!!!!!!!!
        $url = "https://fcm.googleapis.com/fcm/send";
        $token = "/topics/news";
        $serverKey = '*******';
        $title = "KETRAWARS.NET | Interlude x30";
        $body = "Заходи на топовый сервер! Он-лайн 5000+";
        $data = array('message' => $body, 'title' => $title, 'site' => 'ketrawars.net', 'image' => 'img', 'channel' => 'channel_1', 'server_id' => '667');
        $arrayToSend = array('to' => $token, 'data' => $data, 'priority'=>'high');
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
    
    
    
    
    
?>
