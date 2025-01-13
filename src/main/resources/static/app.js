document.addEventListener('DOMContentLoaded', () => {
    const pdfInput = document.getElementById('pdfInput');
    const analyzeButton = document.getElementById('analyzeButton');
    const resultDiv = document.getElementById('result');

    analyzeButton.addEventListener('click', async () => {
        resultDiv.textContent = 'Analyzing...';
        resultDiv.style.color = 'blue';

        const file = pdfInput.files[0];
        if (!file) {
            alert('Please select a PDF file.');
            return;
        }

        try {
            const formData = new FormData();
            formData.append('file', file);

            const response = await fetch('/upload', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.text();
            resultDiv.textContent = data;
            resultDiv.style.color = 'green';
        } catch (error) {
            resultDiv.textContent = 'Error: ' + error.message;
            resultDiv.style.color = 'red';
        }
    });
});
