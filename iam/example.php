<?php

require_once 'vendor/autoload.php';

use RistekUSDI\Kisara\User as KisaraUser;

// First option
$config = [
    'admin_url' => 'http://localhost:8182',
    'base_url' => 'http://localhost:8182',
    'realm' => 'iam-sandbox',
    'client_id' => 'my-client',
    'client_secret' => 'L9Pg92Gyb0Oq9YwBLR85yVRUA8gOpzBt',
];

$data = [
    'firstName' => 'Tsukasa',
    'lastName' => 'Shishio',
    'email' => 'tsukasa@dr.stone',
    'username' => 'tsukasa',
    'enabled' => true,
    'credentials' => [
        [
            'algorithm' => 'MD5',
            'type' => 'password',
            'hashedSaltedValue' => md5('123456789'),
            'hashIterations' => 0,
            // You may set temporary if you want user to reset their password
            // 'temporary' => true,
        ]
    ],
];

$result = (new KisaraUser($config))->store($data);
print_r($result);