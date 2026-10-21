let expression = "";
let resultat = "0";
let historique = [];

function appendChar(caractere) {
    expression = expression + caractere;
    document.getElementById("expression").textContent = expression;
}

function clearAll() {
    expression = "";
    resultat = "0";
    document.getElementById("expression").textContent = "";
    document.getElementById("result").textContent = "0";
}

function clearEntry() {
    expression = "";
    document.getElementById("expression").textContent = "";
}

function backspace() {
    let nouvelle = "";
    for (let i = 0; i < expression.length - 1; i++) {
        nouvelle = nouvelle + expression[i];
    }
    expression = nouvelle;
    document.getElementById("expression").textContent = expression;
}

function convertForCalculation(texte) {
    let nouveau = "";
    for (let i = 0; i < texte.length; i++) {
        if (texte[i] === "x") {
            nouveau = nouveau + "*";
        } else if (texte[i] === "÷") {
            nouveau = nouveau + "/";
        } else {
            nouveau = nouveau + texte[i];
        }
    }
    return nouveau;
}

function hasDivisionByZero(texte) {
    for (let i = 0; i < texte.length; i++) {
        if (texte[i] === "/" && texte[i + 1] === "0") {
            return true;
        }
    }
    return false;
}

function compute() {
    if (expression === "") {
        return;
    }

    let calcul = convertForCalculation(expression);

    if (hasDivisionByZero(calcul)) {
        document.getElementById("result").textContent = "FATAL ERROR";
        expression = "";
        return;
    }

    try {
        let nombre = eval(calcul);
        if (isNaN(nombre)) {
            document.getElementById("result").textContent = "FATAL ERROR";
            expression = "";
            return;
        }
        if (!isFinite(nombre)) {
            document.getElementById("result").textContent = "FATAL ERROR";
            expression = "";
            return;
        }
        resultat = nombre;
        document.getElementById("result").textContent = resultat;
        saveToHistory(expression, resultat);
        expression = "" + resultat;
    } catch (e) {
        document.getElementById("result").textContent = "FATAL ERROR";
        expression = "";
    }
}

function square() {
    if (expression === "") {
        return;
    }
    let calcul = convertForCalculation(expression);
    try {
        let nombre = eval(calcul);
        resultat = nombre * nombre;
        document.getElementById("result").textContent = resultat;
        saveToHistory(expression + "²", resultat);
        expression = "" + resultat;
    } catch (e) {
        document.getElementById("result").textContent = "FATAL ERROR";
        expression = "";
    }
}

function percent() {
    if (expression === "") {
        return;
    }
    let calcul = convertForCalculation(expression);
    try {
        let nombre = eval(calcul);
        resultat = nombre / 100;
        document.getElementById("result").textContent = resultat;
        expression = "" + resultat;
    } catch (e) {
        document.getElementById("result").textContent = "FATAL ERROR";
        expression = "";
    }
}

function toggleSign() {
    if (expression === "") {
        return;
    }
    if (expression[0] === "-") {
        let nouvelle = "";
        for (let i = 1; i < expression.length; i++) {
            nouvelle = nouvelle + expression[i];
        }
        expression = nouvelle;
    } else {
        expression = "-" + expression;
    }
    document.getElementById("expression").textContent = expression;
}

function saveToHistory(expr, res) {
    let entree = {
        expression: expr,
        resultat: res
    };
    historique.push(entree);
    displayHistory();
}

function displayHistory() {
    let liste = document.getElementById("historyList");

    while (liste.firstChild) {
        liste.removeChild(liste.firstChild);
    }

    if (historique.length === 0) {
        let vide = document.createElement("div");
        vide.className = "history-empty";
        vide.textContent = "Aucun calcul";
        liste.appendChild(vide);
        return;
    }

    for (let i = historique.length - 1; i >= 0; i--) {
        let entree = historique[i];

        let div = document.createElement("div");
        div.className = "history-entry";

        let exprDiv = document.createElement("div");
        exprDiv.className = "history-expression";
        exprDiv.textContent = entree.expression + " =";

        let resDiv = document.createElement("div");
        resDiv.className = "history-result";
        resDiv.textContent = entree.resultat;

        div.appendChild(exprDiv);
        div.appendChild(resDiv);
        liste.appendChild(div);
    }
}

function clearHistory() {
    historique = [];
    displayHistory();
}

const boutons = document.querySelectorAll(".btn");

for (let i = 0; i < boutons.length; i++) {
    boutons[i].addEventListener("click", function() {
        let valeur = this.getAttribute("data-value");
        let action = this.getAttribute("data-action");

        if (valeur) {
            appendChar(valeur);
        }

        if (action === "clear") {
            clearAll();
        }
        if (action === "clearEntry") {
            clearEntry();
        }
        if (action === "backspace") {
            backspace();
        }
        if (action === "compute") {
            compute();
        }
        if (action === "square") {
            square();
        }
        if (action === "percent") {
            percent();
        }
        if (action === "toggleSign") {
            toggleSign();
        }
        if (action === "clearHistory") {
            clearHistory();
        }
    });
}

document.addEventListener("keydown", function(event) {
    let touche = event.key;

    if (touche === "0") { appendChar("0"); }
    if (touche === "1") { appendChar("1"); }
    if (touche === "2") { appendChar("2"); }
    if (touche === "3") { appendChar("3"); }
    if (touche === "4") { appendChar("4"); }
    if (touche === "5") { appendChar("5"); }
    if (touche === "6") { appendChar("6"); }
    if (touche === "7") { appendChar("7"); }
    if (touche === "8") { appendChar("8"); }
    if (touche === "9") { appendChar("9"); }
    if (touche === "+") { appendChar("+"); }
    if (touche === "-") { appendChar("-"); }
    if (touche === "*") { appendChar("x"); }
    if (touche === "/") { appendChar("÷"); }
    if (touche === ".") { appendChar("."); }
    if (touche === "Enter") { compute(); }
    if (touche === "Backspace") { backspace(); }
    if (touche === "Escape") { clearAll(); }
});

displayHistory();
