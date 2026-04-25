const { spawn } = require('child_process');

const proc = spawn('railway', ['add'], { env: process.env, shell: true });

let step = 0;

proc.stdout.on('data', (data) => {
    const chunk = data.toString();
    process.stdout.write(chunk);

    if (chunk.includes('What do you need?')) {
        if (step === 0) {
            console.log("\n[SCRIPT] Selecting Database...");
            proc.stdin.write('\x1B[B\r'); // Down arrow + Enter
            step++;
        }
    } else if (chunk.includes('Select databases to add')) {
        if (step === 1) {
            console.log("\n[SCRIPT] Selecting MySQL...");
            proc.stdin.write('\x1B[B'); // Down arrow
            setTimeout(() => {
                proc.stdin.write(' \r'); // Space + Enter
            }, 500);
            step++;
        }
    }
});

proc.stderr.on('data', (data) => {
    console.error(data.toString());
});

proc.on('close', (code) => {
    console.log(`Child process exited with code ${code}`);
});
