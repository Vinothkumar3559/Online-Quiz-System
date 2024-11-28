let questions = [];
let currentQuestionIndex = 0;
let userAnswers = [];
const maxQuestions = 5;

async function fetchQuestions() {
    try {
        const response = await fetch('/quiz/questions');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const allQuestions = await response.json();
        if (!Array.isArray(allQuestions) || allQuestions.length === 0) {
            throw new Error('No questions received from the server');
        }
        questions = allQuestions.slice(0, maxQuestions);
        displayQuestion();
    } catch (error) {
        console.error("Failed to load questions:", error);
        document.getElementById('quiz-content').innerHTML = `
            <p>Failed to load questions. Please try again later.</p>
            <button onclick="location.reload()">Retry</button>
        `;
    }
}

function displayQuestion() {
    if (currentQuestionIndex >= questions.length) {
        submitQuiz();
        return;
    }

    const question = questions[currentQuestionIndex];
    document.getElementById('question-text').textContent = `${currentQuestionIndex + 1}. ${question.questionText}`;
    const optionsContainer = document.getElementById('options-container');
    optionsContainer.innerHTML = '';

    const options = {
        'A': question.optionA,
        'B': question.optionB,
        'C': question.optionC,
        'D': question.optionD
    };

    Object.entries(options).forEach(([key, value]) => {
        const label = document.createElement('label');
        label.innerHTML = `
            <input type="radio" name="option" value="${key}">
            ${value}
        `;
        optionsContainer.appendChild(label);
        optionsContainer.appendChild(document.createElement('br'));
    });

    const progressText = `Question ${currentQuestionIndex + 1} of ${questions.length}`;
    document.getElementById('progress').textContent = progressText;
}


document.getElementById('next-btn').addEventListener('click', () => {
    const selectedOption = document.querySelector('input[name="option"]:checked');
    if (selectedOption) {
        userAnswers.push({
            questionId: questions[currentQuestionIndex].id,
            selectedOption: selectedOption.value
        });
        currentQuestionIndex++;
        displayQuestion();
    } else {
        alert("Please select an answer before proceeding.");
    }
});

async function submitQuiz() {
    try {
        const userId = localStorage.getItem('userId');
        const submission = {
            userId: userId ? parseInt(userId) : null,
            answers: userAnswers
        };

        console.log('Submitting answers:', submission); // Debug log

        const response = await fetch('/quiz/submit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(submission),
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const result = await response.json();
        console.log('Quiz result:', result); // Debug log

        localStorage.setItem('quizScore', result.score);
        window.location.href = '/results';
    } catch (error) {
        console.error("Failed to submit quiz:", error);
        document.getElementById('quiz-content').innerHTML = `
            <p>Failed to submit quiz. Please try again later.</p>
            <button onclick="location.reload()">Retry</button>
        `;
    }
}

fetchQuestions();