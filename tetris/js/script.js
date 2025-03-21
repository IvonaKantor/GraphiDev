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

function showHeader(text) {
    const obj_by_id = document.getElementById('header_text');
    obj_by_id.textContent = text;
    obj_by_id.style.display = 'block';
}

function getNextFigure() {
    const key = Object.keys(figures);
    const letter = key[Math.floor(Math.random() * key.length)];
    return {
        name,
        figures: [letter]
    };
}

setTimeout(() => {
    showHeader('Tetromino');
}, 1000);