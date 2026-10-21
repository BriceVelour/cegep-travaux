//J'ai pas pu faire en modules ES6

const CART_KEY = "lynx_cart";
const TPS = 0.05;
const TVQ = 0.09975;

function getCart() {
  const data = localStorage.getItem(CART_KEY);
  return data ? JSON.parse(data) : [];
}

function saveCart(cart) {
  localStorage.setItem(CART_KEY, JSON.stringify(cart));
}

function addToCart(product) {
  const cart = getCart();
  const existing = cart.find(p => p.id === product.id);

  if (existing) {
    existing.quantity += product.quantity;
  } else {
    cart.push(product);
  }

  saveCart(cart);
  updateCartCounter();
  showToast("Produit ajouté au panier");
}

function removeFromCart(productId) {
  const updatedCart = getCart().filter(item => item.id !== productId);
  saveCart(updatedCart);
  renderCartItems();
  updateCartCounter();
  showToast("Produit supprimé");
}

function updateQuantity(productId, newQty) {
  const cart = getCart();
  const item = cart.find(p => p.id === productId);
  if (item) item.quantity = Math.max(1, parseInt(newQty));
  saveCart(cart);
  renderCartItems();
  updateCartCounter();
  showToast("Quantité mise à jour");
}

function formatPrice(price) {
  return price.toFixed(2).replace('.', ',') + " $ CAD";
}

function calculateTotals(cart) {
  const subtotal = cart.reduce((acc, item) => acc + item.price * item.quantity, 0);
  const tps = subtotal * TPS;
  const tvq = subtotal * TVQ;
  return {
    subtotal,
    tps,
    tvq,
    total: subtotal + tps + tvq
  };
}

function renderCartItems() {
  const cart = getCart();
  const container = document.getElementById("cart-items");
  const summary = document.getElementById("summary-content");
  const empty = document.getElementById("empty-cart");
  const recap = document.getElementById("cart-summary");

  if (!container) return;
  container.innerHTML = "";

  if (cart.length === 0) {
    if (empty) empty.style.display = "block";
    if (recap) recap.style.display = "none";
    return;
  }

  if (empty) empty.style.display = "none";
  if (recap) recap.style.display = "block";

  cart.forEach(item => {
    const article = document.createElement("article");
    article.className = "cart-item row py-4 border-bottom align-items-center";
    article.dataset.id = item.id;

    article.innerHTML = `
      <div class="col-md-6 d-flex align-items-center mb-3 mb-md-0">
        <img src="${item.image}" class="img-fluid rounded me-3" width="80" alt="${item.name}">
        <div>
          <h3 class="h5 mb-1">${item.name}</h3>
          <p class="text-muted small">Format standard</p>
        </div>
      </div>
      <div class="col-6 col-md-2 text-md-center">${formatPrice(item.price)}</div>
      <div class="col-6 col-md-2 text-md-center">
        <input type="number" class="form-control form-control-sm quantity-input" value="${item.quantity}" min="1" max="10" data-id="${item.id}">
        <button class="btn btn-sm update-btn mt-2" data-id="${item.id}"><i class="bi bi-arrow-clockwise"></i></button>
      </div>
      <div class="col-6 col-md-1 text-md-center">${formatPrice(item.price * item.quantity)}</div>
      <div class="col-6 col-md-1 text-md-center">
        <button class="btn btn-sm btn-outline-danger delete-btn" data-id="${item.id}"><i class="bi bi-trash"></i></button>
      </div>
    `;
    container.appendChild(article);
  });

  attachCartEvents();

  if (summary) {
    const { subtotal, tps, tvq, total } = calculateTotals(cart);
    summary.innerHTML = `
      <div class="d-flex justify-content-between mb-2"><span>Sous-total</span><span>${formatPrice(subtotal)}</span></div>
      <div class="d-flex justify-content-between mb-2"><span>TPS (5%)</span><span>${formatPrice(tps)}</span></div>
      <div class="d-flex justify-content-between mb-2"><span>TVQ (9.975%)</span><span>${formatPrice(tvq)}</span></div>
      <div class="d-flex justify-content-between fw-bold border-top pt-3"><span>Total</span><span>${formatPrice(total)}</span></div>
    `;
  }
}

function attachCartEvents() {
  document.querySelectorAll(".update-btn").forEach(btn =>
    btn.addEventListener("click", () => {
      const id = btn.dataset.id;
      const qty = document.querySelector(`.quantity-input[data-id="${id}"]`).value;
      updateQuantity(id, qty);
    })
  );

  document.querySelectorAll(".delete-btn").forEach(btn =>
    btn.addEventListener("click", () => removeFromCart(btn.dataset.id))
  );
}

function showToast(message) {
  const toastEl = document.getElementById("toast-action") || document.getElementById("toast-panier");
  const toastMsg = document.getElementById("toast-message");

  if (!toastEl) return;
  if (toastMsg) toastMsg.textContent = message;

  const toast = new bootstrap.Toast(toastEl, { animation: true, delay: 3000 });
  toast.show();
}

function updateCartCounter() {
  const counter = document.getElementById("cart-counter");
  if (!counter) return;
  const total = getCart().reduce((acc, item) => acc + item.quantity, 0);
  counter.textContent = total;
  counter.style.display = total > 0 ? "inline-block" : "none";
}

function renderOrderSummaryPage() {
  const cart = getCart();
  const checkout = document.getElementById("checkout-content");
  const empty = document.getElementById("empty-cart-message");
  const items = document.getElementById("order-items");
  const summary = document.getElementById("order-summary");

  if (cart.length === 0) {
    if (checkout) checkout.style.display = "none";
    if (empty) empty.style.display = "block";
    return;
  }

  if (checkout) checkout.style.display = "flex";
  if (empty) empty.style.display = "none";

  if (items) {
    items.innerHTML = "";
    cart.forEach(item => {
      const div = document.createElement("div");
      div.className = "d-flex justify-content-between align-items-center mb-3";
      div.innerHTML = `
        <div class="d-flex align-items-center">
          <img src="${item.image}" class="img-fluid rounded me-3" width="60" alt="${item.name}">
          <div>
            <h5 class="mb-0">${item.name}</h5>
            <small class="text-muted">Quantité : ${item.quantity}</small>
          </div>
        </div>
        <strong>${formatPrice(item.price * item.quantity)}</strong>
      `;
      items.appendChild(div);
    });
  }

  if (summary) {
    const { subtotal, tps, tvq, total } = calculateTotals(cart);
    summary.innerHTML = `
      <div class="d-flex justify-content-between mb-2"><span>Sous-total</span><span>${formatPrice(subtotal)}</span></div>
      <div class="d-flex justify-content-between mb-2"><span>TPS (5%)</span><span>${formatPrice(tps)}</span></div>
      <div class="d-flex justify-content-between mb-2"><span>TVQ (9.975%)</span><span>${formatPrice(tvq)}</span></div>
      <div class="d-flex justify-content-between fw-bold border-top pt-3"><span>Total</span><span>${formatPrice(total)}</span></div>
    `;
  }

  const confirm = document.getElementById("confirm-order-btn");
  if (confirm) {
    confirm.addEventListener("click", () => {
      const orderId = "LYNX-" + Math.floor(Math.random() * 1000000);
      const orderDisplay = document.getElementById("orderNumber");
      if (orderDisplay) orderDisplay.textContent = orderId;
      const modal = new bootstrap.Modal(document.getElementById("confirmationModal"));
      modal.show();
      localStorage.removeItem(CART_KEY);
      updateCartCounter();
    });
  }

  const credit = document.getElementById("creditCard");
  const paypal = document.getElementById("paypal");
  const details = document.getElementById("creditCardDetails");

  if (credit && paypal && details) {
    function togglePayment() {
      details.style.display = credit.checked ? "block" : "none";
    }
    credit.addEventListener("change", togglePayment);
    paypal.addEventListener("change", togglePayment);
    togglePayment();
  }
}

document.addEventListener("DOMContentLoaded", () => {
  updateCartCounter();

  const page = window.location.pathname;

  if (page.includes("produits.html")) {
    document.querySelectorAll(".add-to-cart-btn").forEach(btn =>
      btn.addEventListener("click", () => {
        const id = btn.dataset.id;
        const name = btn.dataset.name;
        const price = parseFloat(btn.dataset.price);
        const image = btn.dataset.image;
        const category = btn.dataset.category;
        const qty = parseInt(document.getElementById("qty-" + id).value);

        if (qty < 1) {
          alert("Veuillez entrer une quantité valide.");
          return;
        }

        addToCart({ id, name, price, image, category, quantity: qty });
        document.getElementById("qty-" + id).value = 1;
      })
    );

    const filters = document.querySelectorAll(".filter-btn");
    const products = document.querySelectorAll(".product-item");

    filters.forEach(btn =>
      btn.addEventListener("click", () => {
        filters.forEach(b => b.classList.remove("active"));
        btn.classList.add("active");
        const filter = btn.dataset.filter;
        products.forEach(p =>
          p.style.display = filter === "tous" || p.dataset.category === filter ? "block" : "none"
        );
      })
    );
  }

  if (page.includes("panier.html")) renderCartItems();
  if (page.includes("paiement.html")) renderOrderSummaryPage();
});
