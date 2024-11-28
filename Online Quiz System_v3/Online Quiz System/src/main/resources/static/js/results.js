document.addEventListener('DOMContentLoaded', function() {
    const score = localStorage.getItem('quizScore');
    const scoreElement = document.getElementById('score');

    if (score !== null) {
        scoreElement.textContent = score;
    } else {
        scoreElement.textContent = 'Score not available';
        console.error('No score found in localStorage');
    }
});

function restartQuiz() {
    localStorage.removeItem('quizScore');
    window.location.href = '/quiz';
}

function goToHomePage() {
    localStorage.removeItem('quizScore');
    window.location.href = '/index';
}