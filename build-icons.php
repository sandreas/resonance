#!/usr/bin/env php
<?php

function camelCase($str) {
    $str = str_replace(' ', '',
        ucwords(str_replace(['-', '_'],
        ' ', $str))
    );

    return $str;
}


$template = file_get_contents(__DIR__."/build-icons.kttpl");


$destinationPath = "app/src/main/java/com/pilabor/resonance/icons/";
$svgPath = "resources/icons/";
$svgs = glob($svgPath . "*.svg");

foreach($svgs as $svg) {
  $base = basename($svg);
  $name = explode(".", $base)[0];
  $ccName = camelCase($name);


  $xml = simplexml_load_file($svg);

  $paths = [];

  foreach($xml->path as $path) {
      foreach($path->attributes() as $key => $value) {
        if($key == "d") {
          $paths[] = (string)$value;
        }
      }
  }

  $combinedPath = implode(" ", $paths);

  $replacedTemplate = strtr($template, [
                                           "{NAME}" => $ccName,
                                           "{PATH}" => $combinedPath
                                         ]);
  $destinationFile = $destinationPath."/".$ccName.".kt";
  file_put_contents($destinationFile, $replacedTemplate);
  // echo $ccName.PHP_EOL;
}
