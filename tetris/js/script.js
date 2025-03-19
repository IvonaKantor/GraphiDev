const canvas = document.getElementById('game');
const context = canvas.getContext('2d');
const grid = 32;
const field = [];
const figures = {
    'I': [[0, 0, 0, 0], [1, 1, 1, 1], [0, 0, 0, 0], [0, 0, 0, 0]],
    'J': [[1, 0, 0], [1, 1, 1], [0, 0, 0]],
    'L': [[0, 0, 1], [1, 1, 1], [0, 0, 0]],
    'O': [[1, 1], [1, 1]],
    'S': [[0, 1, 1], [1, 1, 0], [0, 0, 0]],
    'Z': [[1, 1, 0], [0, 1, 1], [0, 0, 0]],
    'T': [[0, 1, 0], [1, 1, 1], [0, 0, 0]]
};
const colors = {
    'I': 'cyan', 'O': 'yellow', 'T': 'purple', 'S': 'green', 'Z': 'red', 'J': 'blue', 'L': 'orange'
};

for (let i = 0; i < 20; i++) {
    field.push(new Array(10).fill(0));
}
let count = 0;
let actual_figure = getNextFigure();
let rAF = null;
let gameOver = false;

//const music1 = new Audio('tetrisb.mid');
//const music2 = new Audio('tetrisc.mid');

function showTextScreen(message) {
    const textScreen = document.getElementById('textScreen');

    textScreen.textContent = message;
    textScreen.style.display = 'block';
    /*setTimeout(() => {
        textScreen.style.display = 'none';
    }, 2000);*/ // Text will show for 2 seconds
}

function getNextFigure() {
    const keys = Object.keys(figures);
    const name = keys[Math.floor(Math.random() * keys.length)];
    return {
        name,
        matrix: figures[name],
        row: 0,
        col: Math.floor(10 / 2) - Math.floor(figures[name][0].length / 2)
    };
}

function isValidMove(matrix, row, col) {
    for (let r = 0; r < matrix.length; r++) {
        for (let c = 0; c < matrix[r].length; c++) {
            if (matrix[r][c] && (
                col + c < 0 || col + c >= 10 || row + r >= 20 || field[row + r][col + c])
            ) {
                return false;
            }
        }
    }
    return true;
}

function placeTetromino() {
    for (let r = 0; r < actual_figure.matrix.length; r++) {
        for (let c = 0; c < actual_figure.matrix[r].length; c++) {
            if (actual_figure.matrix[r][c]) {
                field[actual_figure.row + r][actual_figure.col + c] = actual_figure.name;
            }
        }
    }
    actual_figure = getNextFigure();
    if (!isValidMove(actual_figure.matrix, actual_figure.row, actual_figure.col)) {
        gameOver = true;
        showTextScreen('Game Over');
        cancelAnimationFrame(rAF);
        //stopMusic();
    }
}

/*function stopMusic() {
    music1.pause();
    music2.pause();
}

function playMusic() {
    stopMusic();
    if (Math.random() < 0.5) {
        music1.currentTime = 0;
        music1.play();
    } else {
        music2.currentTime = 0;
        music2.play();
    }
}*/

function rotate(matrix) {
    return matrix[0].map((_, i) => matrix.map(row => row[i])).reverse();
}

function loop() {
    if (gameOver) return;
    rAF = requestAnimationFrame(loop);
    context.clearRect(0, 0, canvas.width, canvas.height);

    for (let row = 0; row < 20; row++) {
        for (let col = 0; col < 10; col++) {
            if (field[row][col]) {
                context.fillStyle = colors[field[row][col]];
                context.fillRect(col * grid, row * grid, grid - 1, grid - 1);
            }
        }
    }

    if (++count > 35) {
        actual_figure.row++;
        count = 0;
        if (!isValidMove(actual_figure.matrix, actual_figure.row, actual_figure.col)) {
            actual_figure.row--;
            placeTetromino();
        }
    }

    context.fillStyle = colors[actual_figure.name];
    actual_figure.matrix.forEach((row, r) => row.forEach((val, c) => {
        if (val) {
            context.fillRect((actual_figure.col + c) * grid, (actual_figure.row + r) * grid, grid - 1, grid - 1);
        }
    }));
}

setTimeout(() => {
    showTextScreen('Tetromino');
    //playMusic();
    loop();
}, 1000);

document.addEventListener('keydown', function (e) {
    if (gameOver) return;

    if (e.key === 'ArrowLeft' || e.key === 'ArrowRight') {
        const col = e.key === 'ArrowLeft' ? actual_figure.col - 1 : actual_figure.col + 1;
        if (isValidMove(actual_figure.matrix, actual_figure.row, col)) actual_figure.col = col;
    }

    if (e.key === 'ArrowUp') {
        const matrix = rotate(actual_figure.matrix);
        if (isValidMove(matrix, actual_figure.row, actual_figure.col)) actual_figure.matrix = matrix;
    }

    if (e.key === 'ArrowDown') {
        let row = actual_figure.row + 1;
        if (!isValidMove(actual_figure.matrix, row, actual_figure.col)) {
            actual_figure.row = row - 1;
            placeTetromino();
        } else {
            actual_figure.row = row;
        }
    }
});