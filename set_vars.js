const { execSync } = require('child_process');

try {
    console.log("Setting variables...");
    // Use CMD to bypass PS parsing issues
    execSync('railway variables set "MYSQLHOST=${{ MySQL.MYSQLHOST }}" "MYSQLPORT=${{ MySQL.MYSQLPORT }}" "MYSQLUSER=${{ MySQL.MYSQLUSER }}" "MYSQLPASSWORD=${{ MySQL.MYSQLPASSWORD }}" "MYSQLDATABASE=${{ MySQL.MYSQLDATABASE }}"', { stdio: 'inherit', shell: 'cmd.exe' });
    console.log("Variables set successfully.");
} catch (e) {
    console.error("Error setting variables:", e);
}
