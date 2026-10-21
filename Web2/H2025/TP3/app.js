// Variables globales pour stocker les étudiants et les notes
const students = [];
const notes = [];

// Fonction pour valider les données du formulaire
function validateForm(nom, prenom, note) {
  const errors = [];

  // Vérifie que les champs nom et prénom ne sont pas vides
  if (nom.trim() === "") {
    errors.push("Le champ 'Nom' est obligatoire.");
  }
  if (prenom.trim() === "") {
    errors.push("Le champ 'Prénom' est obligatoire.");
  }

  // Vérifie que la note est valide
  if (note === "" || isNaN(note)) {
    errors.push("La note doit être un chiffre.");
  } else if (note < 0 || note > 100) {
    errors.push("La note doit être comprise entre 0 et 100.");
  }

  // Vérifie si l'étudiant existe déjà
  for (let i = 0; i < students.length; i++) {
    const student = students[i];
    if (
      student.nom.toLowerCase() === nom.toLowerCase() &&
      student.prenom.toLowerCase() === prenom.toLowerCase()
    ) {
      errors.push("Cet étudiant existe déjà.");
      break;
    }
  }

  return errors;
}

// Fonction pour afficher les erreurs
function displayErrors(errors) {
  const errorContainer = document.querySelector("#errorMessages");

  if (errors.length > 0) {
    // Affiche les erreurs dans un paragraphe HTML
    let errorHTML = "";
    for (let i = 0; i < errors.length; i++) {
      errorHTML += "<p>" + errors[i] + "</p>";
    }
    errorContainer.innerHTML = errorHTML;
    errorContainer.classList.remove("d-none");
  } else {
    // Cache la section des erreurs
    errorContainer.classList.add("d-none");
    errorContainer.innerHTML = "";
  }
}

// Fonction pour ajouter un étudiant
function addStudent(nom, prenom, note) {
  const student = {
    nom: nom,
    prenom: prenom,
    note: parseFloat(note),
  };

  students.push(student);
  notes.push(parseFloat(note));

  // Tri des étudiants par ordre alphabétique du nom
  students.sort(function (a, b) {
    if (a.nom < b.nom) {
      return -1;
    } else if (a.nom > b.nom) {
      return 1;
    } else {
      return 0;
    }
  });
}

// Fonction pour afficher les étudiants dans le tableau
function renderTable() {
  const tableBody = document.querySelector("#studentsTable tbody");
  tableBody.innerHTML = ""; // Réinitialise le contenu du tableau

  for (let i = 0; i < students.length; i++) {
    const student = students[i];
    let statut = "Échec";
    if (student.note >= 60) {
      statut = "Succès";
    }

    // Ajout d'une nouvelle ligne au tableau
    const row = `
            <tr>
                <td>${student.nom} ${student.prenom}</td>
                <td>${student.note}%</td>
                <td>${statut}</td>
            </tr>`;
    tableBody.innerHTML += row;
  }
}

// Fonction pour calculer la note minimale
function calculateMin(notes) {
  let min = notes[0];
  for (let i = 1; i < notes.length; i++) {
    if (notes[i] < min) {
      min = notes[i];
    }
  }
  return min;
}

// Fonction pour calculer la note maximale
function calculateMax(notes) {
  let max = notes[0];
  for (let i = 1; i < notes.length; i++) {
    if (notes[i] > max) {
      max = notes[i];
    }
  }
  return max;
}

// Fonction pour calculer la moyenne
function calculateAverage(notes) {
  let sum = 0;
  for (let i = 0; i < notes.length; i++) {
    sum += notes[i];
  }
  return (sum / notes.length).toFixed(2);
}

// Fonction pour calculer la médiane
function calculateMedian(notes) {
  const sortedNotes = notes.slice().sort(function (a, b) {
    return a - b;
  });
  const middle = Math.floor(sortedNotes.length / 2);

  if (sortedNotes.length % 2 === 0) {
    const median = (sortedNotes[middle - 1] + sortedNotes[middle]) / 2;
    return median.toFixed(2);
  } else {
    return sortedNotes[middle];
  }
}

// Fonction pour calculer l'écart type
function calculateStdDev(notes) {
  const average = calculateAverage(notes);
  let variance = 0;
  for (let i = 0; i < notes.length; i++) {
    variance += Math.pow(notes[i] - average, 2);
  }
  variance = variance / notes.length;
  return Math.sqrt(variance).toFixed(2);
}

// Fonction pour mettre à jour les statistiques dans l'interface
function updateStats() {
  if (notes.length === 0) {
    document.querySelector("#minNote").textContent = "-";
    document.querySelector("#maxNote").textContent = "-";
    document.querySelector("#totalNotes").textContent = "-";
    document.querySelector("#average").textContent = "-";
    document.querySelector("#median").textContent = "-";
    document.querySelector("#stdDev").textContent = "-";
    return;
  }

  document.querySelector("#minNote").textContent = calculateMin(notes);
  document.querySelector("#maxNote").textContent = calculateMax(notes);
  document.querySelector("#totalNotes").textContent = notes.length;
  document.querySelector("#average").textContent = calculateAverage(notes);
  document.querySelector("#median").textContent = calculateMedian(notes);
  document.querySelector("#stdDev").textContent = calculateStdDev(notes);
}

// Gestionnaire pour le formulaire
document.querySelector("#studentForm").addEventListener("submit", function (e) {
  e.preventDefault();

  // Récupérer les valeurs des champs
  const nom = document.querySelector("#nom").value;
  const prenom = document.querySelector("#prenom").value;
  const note = document.querySelector("#note").value;

  // Valider les données
  const errors = validateForm(nom, prenom, note);
  displayErrors(errors);

  if (errors.length > 0) {
    return;
  }

  // Ajouter l'étudiant
  addStudent(nom, prenom, note);

  // Mettre à jour le tableau et les statistiques
  renderTable();
  updateStats();

  // Réinitialiser le formulaire
  document.querySelector("#studentForm").reset();
  displayErrors([]);
});
