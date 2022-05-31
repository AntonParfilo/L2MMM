<?php

     
    $naw_date = date("Y-m-d h:i:s");
    echo $naw_date;
    echo"</br>";
    

    $d = DateTime::createFromFormat('Y-m-d h:i:s', $naw_date);
    if ($d === false) {
        die("Incorrect date string");
    } else {
        echo $d->getTimestamp();
    }

   

?>
