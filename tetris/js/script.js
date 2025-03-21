const canvas = document.getElementById('game');
const context = canvas.getContext('2d');
const blockSize = 32;
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

let actual_figure = getNextFigure();
let gameOver = false;
let clearedLines = 0;
let fallSpeed = 0;

function showHeader(text) {
    const obj_by_id = document.getElementById('header_text');
    obj_by_id.textContent = text;
    obj_by_id.style.display = 'block';
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

function checkPlacement(matrix, row, col) {
    return matrix.every((r, i) =>
        r.every((cell, j) =>
            !cell || (field[row + i]?.[col + j] === 0 && row + i < 20 && col + j >= 0 && col + j < 10)
        )
    );
}

function place() {
    for (let i = 0; i < actual_figure.matrix.length; i++) {
        for (let j = 0; j < actual_figure.matrix[i].length; j++) {
            if (actual_figure.matrix[i][j]) {
                field[actual_figure.row + i][actual_figure.col + i] = actual_figure.name;
            }
        }
    }

    deleteLine();
    actual_figure = getNextFigure();
    if (!checkPlacement(actual_figure.matrix, actual_figure.row, actual_figure.col, actual_figure.col)) {
        gameOver = true;
        showHeader('Game Over');
    }
}

function rotate(matrix) {
    return matrix[0].map((_, i) => matrix.map(row => row[i])).reverse();
}

function deleteLine() {
    for (let row = 19; row >= 0; row--) {
        if (!field[row].includes(0)) {
            field.splice(row, 1);
            field.unshift(Array(10).fill(0));
            document.getElementById('lines-cleared').textContent = `Lines Cleared: ${++linesCleared}`;
            row++;
        }
    }
}

setTimeout(() => {
    showHeader('Tetromino');
}, 1000);