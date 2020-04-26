#前回スクリプト実行時から更新があるか調べる
function checkLastExec($resultFile, $logFile) {
    #前回スクリプト実行日時を調べる
    $lastExecTime = (Get-ItemProperty $resultFile).LastWriteTime

    #指定したファイルの更新日時を調べる
    $logFileUpdatedTime = (Get-ItemProperty .\$logFile).LastWriteTime

    return ($lastExecTime -lt $logFileUpdatedTime)
}

#行数を出力する
function outputLines($logFile) {
    #全体の行数を取得
    $totalLines = (Get-Content -Path .\$logFile).Length

    #コメント行数を取得
    $commentLines = (Select-String "^#" .\$logFile).Count

    #ログの行数を求める
    $logLines = $totalLines - $commentLines

    return $logLines
}

#スクリプトの置き場を取得する
$scriptPath = Convert-Path .
#対象フォルダを指定する
$targetFolder = "C:\inetpub\logs\LogFiles\W3SVC1\"
#結果ファイルの名前
$resultFile = "C:\Users\nakanoya\Desktop\アクセス数出力\iis-log-lines.txt"
#アクセス数
$accessCount = 0

cd $targetFolder

#対象フォルダからファイルを取得する
$logFileList = Get-ChildItem -Name .\
foreach($logFile in $logFileList) {
    $checkUpdate = checkLastExec $resultFile $logFile

    if($checkUpdate) {
        $accessCount += outputLines $logFile
    }else {
    }   
}

echo $accessCount > $resultFile