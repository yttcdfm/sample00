#新しくフォルダを作り、その中にフォルダコピー
function copyFolder($folderFrom, $folderTo){
    #フォルダの作成
    New-Item .\$folderTo -ItemType Directory | Out-Null

    #コピー
    Copy-Item $folderFrom -Destination .\$folderTo -Recurse
}

#ログのバックアップ
function backupLog {
    #フォルダの作成

    #イベントログのバックアップ
    copyFolder "C:\Windows\System32\winevt\EventLogs" "event"
}

#DBのバックアップ
function backupDb {

    #バックアップ処理
    while($true){
        Write-Host "バックアップします"
        $retryBackupDB = Read-Host "DBのバックアップをリトライしますか(y/n)"
        if($retryBackupDB -eq "y" -or $retryBackupDB -eq "Y"){

        }elseif($retryBackupDB -eq "n" -or $retryBackupDB -eq "N"){
            Write-Host -NoNewline "フォルダを圧縮します。よろしければEnterを押してください"
            [Console]::ReadKey($true) | Out-Null
            break
        }else{
            Write-Host "入力値が違います。yかnで入力してください。"
        }
    }
}

#現在日付の取得
$workDirectoryName = Get-Date -Format "yyMMdd_HHmm"

#作業フォルダの作成
cd "C:\Users\nakanoya\Desktop"
New-Item .\$workDirectoryName -ItemType Directory | Out-Null

cd .\$workDirectoryName

backupLog

while($true){
    $execBackupDB = Read-Host "DBのバックアップをしますか(y/n)"

    if($execBackupDB -eq "y" -or $execBackupDB -eq "Y"){
        backupDb
        break
    }elseif($execBackupDB -eq "n" -or $execBackupDB -eq "N"){
        break
    }else{
        Write-Host "バックアップする場合はy,しない場合はnを入力してください"
    }
}

#フォルダの圧縮
$folderList = Get-ChildItem .\ -Name
foreach($folder in $folderList){
    Compress-Archive .\$folder .\$folder
}