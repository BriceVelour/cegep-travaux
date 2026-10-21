document.addEventListener("DOMContentLoaded", function() {
    const cart = getCart();

    if (cart.length === 0) {
        document.getElementById("form-section").classList.add("d-none");
        document.getElementById("empty-cart-redirect").classList.remove("d-none");
        return;
    }

    renderOrderSummary();
    initForm();
});

function renderOrderSummary() {
    const cart = getCart();
    const container = document.getElementById("summary-items");

    container.innerHTML = "";

    for (const item of cart) {
        const div = document.createElement("div");
        div.className = "summary-line mb-2";

        const nameSpan = document.createElement("span");
        nameSpan.innerHTML = item.name + ' <small class="text-muted">x' + item.quantity + '</small>';

        const priceSpan = document.createElement("span");
        priceSpan.textContent = "$" + (item.price * item.quantity).toFixed(2);

        div.appendChild(nameSpan);
        div.appendChild(priceSpan);

        container.appendChild(div);
    }

    const subtotal = calculateTotal();

    let shipping = 9.99;
    if (subtotal >= 50) {
        shipping = 0;
    }

    const taxes = subtotal * 0.15;
    const total = subtotal + shipping + taxes;

    document.getElementById("subtotal").textContent = "$" + subtotal.toFixed(2);

    if (shipping === 0) {
        document.getElementById("shipping").textContent = "Free";
    } else {
        document.getElementById("shipping").textContent = "$" + shipping.toFixed(2);
    }

    document.getElementById("taxes").textContent = "$" + taxes.toFixed(2);
    document.getElementById("total").textContent = "$" + total.toFixed(2);
}

function initForm() {
    const form = document.getElementById("payment-form");

    const cardInput = document.getElementById("card-number");
    cardInput.addEventListener("input", function() {
        let value = this.value.replace(/\s/g, "").replace(/\D/g, "");
        let formatted = "";

        for (let i = 0; i < value.length; i++) {
            if (i > 0 && i % 4 === 0) {
                formatted = formatted + " ";
            }
            formatted = formatted + value[i];
        }

        this.value = formatted;
    });

    const expirationInput = document.getElementById("expiration");
    expirationInput.addEventListener("input", function() {
        let value = this.value.replace(/\D/g, "");

        if (value.length >= 2) {
            value = value.substring(0, 2) + "/" + value.substring(2, 4);
        }

        this.value = value;
    });

    const phoneInput = document.getElementById("phone");
    phoneInput.addEventListener("input", function() {
        let value = this.value.replace(/\D/g, "");
        let formatted = "";

        if (value.length > 0) {
            formatted = value.substring(0, 3);
        }
        if (value.length > 3) {
            formatted = formatted + "-" + value.substring(3, 6);
        }
        if (value.length > 6) {
            formatted = formatted + "-" + value.substring(6, 10);
        }

        this.value = formatted;
    });

    const postalInput = document.getElementById("postalcode");
    postalInput.addEventListener("input", function() {
        let value = this.value.toUpperCase().replace(/[^A-Z0-9]/g, "");

        if (value.length > 3) {
            value = value.substring(0, 3) + " " + value.substring(3, 6);
        }

        this.value = value;
    });

    form.addEventListener("submit", function(e) {
        e.preventDefault();

        const allInputs = form.querySelectorAll(".form-control, .form-select");
        for (const input of allInputs) {
            input.classList.remove("is-invalid");
        }

        let isValid = true;

        if (validateField("firstname", validateText) === false) {
            isValid = false;
        }
        if (validateField("lastname", validateText) === false) {
            isValid = false;
        }
        if (validateField("email", validateEmail) === false) {
            isValid = false;
        }
        if (validateField("phone", validatePhone) === false) {
            isValid = false;
        }
        if (validateField("address", validateText) === false) {
            isValid = false;
        }
        if (validateField("city", validateText) === false) {
            isValid = false;
        }
        if (validateField("province", validateSelect) === false) {
            isValid = false;
        }
        if (validateField("postalcode", validatePostalCode) === false) {
            isValid = false;
        }
        if (validateField("card-name", validateText) === false) {
            isValid = false;
        }
        if (validateField("card-number", validateCardNumber) === false) {
            isValid = false;
        }
        if (validateField("expiration", validateExpiration) === false) {
            isValid = false;
        }
        if (validateField("cvv", validateCVV) === false) {
            isValid = false;
        }

        if (isValid) {
            confirmOrder();
        }
    });
}

function validateField(id, validationFunction) {
    const input = document.getElementById(id);
    const value = input.value.trim();

    if (validationFunction(value) === false) {
        input.classList.add("is-invalid");
        return false;
    }

    return true;
}

function validateText(value) {
    if (value.length >= 2) {
        return true;
    }
    return false;
}

function validateEmail(value) {
    if (value.includes("@") && value.includes(".")) {
        return true;
    }
    return false;
}

function validatePhone(value) {
    if (value.length === 12 && value[3] === "-" && value[7] === "-") {
        return true;
    }
    return false;
}

function validatePostalCode(value) {
    const cleaned = value.replace(" ", "");
    if (cleaned.length === 6) {
        return true;
    }
    return false;
}

function validateSelect(value) {
    if (value.length > 0) {
        return true;
    }
    return false;
}

function validateCardNumber(value) {
    const noSpaces = value.replace(/\s/g, "");
    if (noSpaces.length === 16) {
        return true;
    }
    return false;
}

function validateExpiration(value) {
    if (value.length === 5 && value[2] === "/") {
        const month = parseInt(value.substring(0, 2));
        if (month >= 1 && month <= 12) {
            return true;
        }
    }
    return false;
}

function validateCVV(value) {
    if (value.length === 3 || value.length === 4) {
        return true;
    }
    return false;
}

function confirmOrder() {
    const randomNumber = Math.floor(100000 + Math.random() * 900000);
    const orderNumber = "#MS-" + randomNumber;

    document.getElementById("order-number").textContent = orderNumber;

    clearCart();

    document.getElementById("form-section").classList.add("d-none");
    document.getElementById("order-confirmation").classList.remove("d-none");

    window.scrollTo(0, 0);
}
