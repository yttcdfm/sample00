#�V�����t�H���_�����A���̒��Ƀt�H���_�R�s�[
function copyFolder($folderFrom, $folderTo){
    #�t�H���_�̍쐬
    New-Item .\$folderTo -ItemType Directory | Out-Null

    #�R�s�[
    Copy-Item $folderFrom -Destination .\$folderTo -Recurse
}

#���O�̃o�b�N�A�b�v
function backupLog {
    #�t�H���_�̍쐬

    #�C�x���g���O�̃o�b�N�A�b�v
    copyFolder "C:\Windows\System32\winevt\EventLogs" "event"
}

#DB�̃o�b�N�A�b�v
function backupDb {

    #�o�b�N�A�b�v����
    while($true){
        Write-Host "�o�b�N�A�b�v���܂�"
        $retryBackupDB = Read-Host "DB�̃o�b�N�A�b�v�����g���C���܂���(y/n)"
        if($retryBackupDB -eq "y" -or $retryBackupDB -eq "Y"){

        }elseif($retryBackupDB -eq "n" -or $retryBackupDB -eq "N"){
            Write-Host -NoNewline "�t�H���_�����k���܂��B��낵�����Enter�������Ă�������"
            [Console]::ReadKey($true) | Out-Null
            break
        }else{
            Write-Host "���͒l���Ⴂ�܂��By��n�œ��͂��Ă��������B"
        }
    }
}

#���ݓ��t�̎擾
$workDirectoryName = Get-Date -Format "yyMMdd_HHmm"

#��ƃt�H���_�̍쐬
cd "C:\Users\nakanoya\Desktop"
New-Item .\$workDirectoryName -ItemType Directory | Out-Null

cd .\$workDirectoryName

backupLog

while($true){
    $execBackupDB = Read-Host "DB�̃o�b�N�A�b�v�����܂���(y/n)"

    if($execBackupDB -eq "y" -or $execBackupDB -eq "Y"){
        backupDb
        break
    }elseif($execBackupDB -eq "n" -or $execBackupDB -eq "N"){
        break
    }else{
        Write-Host "�o�b�N�A�b�v����ꍇ��y,���Ȃ��ꍇ��n����͂��Ă�������"
    }
}

#�t�H���_�̈��k
$folderList = Get-ChildItem .\ -Name
foreach($folder in $folderList){
    Compress-Archive .\$folder .\$folder
}