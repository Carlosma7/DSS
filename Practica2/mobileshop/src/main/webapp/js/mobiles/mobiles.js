// Event listener to Add Mobile form button
formAddMobile.addEventListener('submit', (event) => {
    event.preventDefault();
    
    // Add mobile   	
	addMobile(
	    document.querySelector('#brand').value,
	    document.querySelector('#model').value,
	    document.querySelector('#os').value,
	    document.querySelector('#color').value,
	    parseInt(document.querySelector('#year').value),
	    parseInt(document.querySelector('#price').value)
	)
});

// Event listener to Exit Shopping cart button
formButtonExitCart.addEventListener('submit', (event) => {
    event.preventDefault();
	
	// Make list of mobiles visible
    listMobilesVis.classList.add("visuallyvisible");
	listMobilesVis.classList.remove("visuallyhidden");
	
	// Make shopping cart and logout buttons visible
	shoppingCartVis.classList.add("visuallyvisible");
	shoppingCartVis.classList.remove("visuallyhidden");
	clientLogoutVis.classList.add("visuallyvisible");
	clientLogoutVis.classList.remove("visuallyhidden");
	
	// Hide shopping cart list
	listShoppingCartVis.classList.add("visuallyhidden");
    listShoppingCartVis.classList.remove("visuallyvisible");
});

// Add mobile
async function addMobile(brand, model, os, color, year, price){
	// Add mobile POST request
    let response = await fetch('http://localhost:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/mobiles',{
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({brand, model, os, color, year, price})
    });
    
    // Update list of mobiles
    generateMobilesAdmin();
}

// Remove mobile by id
function removeMobile(id){
    // Remove mobile DELETE request
    fetchAsync('http://localhost:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/mobiles/'+id, 'DELETE').then((result) => {
        console.log("Resultado de la operacion: " + result);
        generateMobilesAdmin();
    })
}

// Remove item in shopping cart
function removeShoppingCart(id){
    // Remove item shopping cart DELETE request
	fetchAsync('http://localhost:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/shoppingcart/'+id, 'DELETE').then((result) => {
        console.log("Resultado de la operacion: " + result);
        generateShoppingCart();
    })
}

// Add to shopping cart an item
async function addToShoppingCart(mobile){
    // Get actual user cookie
    var client = getCookie('actual_user');
    
    // Add item to shopping cart POST request
    let response = await fetch('http://localhost:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/shoppingcart/add',{
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({client,mobile})
    });
    
    // Update list of mobiles
    generateMobiles();
}

// Fetch Async function
async function fetchAsync (url, methodHttp) {
  let response = await fetch(url,{
      method: methodHttp,
      headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
      }
  });
  
  let data;
  if(methodHttp != 'DELETE'){
  	data = await response.json();
  }else{
  	data = await response;
  }
  return data;
}

// Fetch Async function with body
async function fetchAsyncPost (url, methodHttp, post) {
  let response = await fetch(url,{
      method: methodHttp,
      headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
      },
      body: post
  });
  let data = await response.json();
  return data;
}

// Generate mobiles list
function generateMobiles(){

	// Get list of mobiles GET request
    fetchAsync('http://localhost:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/mobiles', 'GET').then( (data) => {
        // Get element of mobiles table
        let htmlTable = document.getElementById('mobiles_table');
        htmlTable.innerHTML = "";
        let table = document.createElement('table');
        let tableBody = document.createElement('tbody');
        table.appendChild(tableBody);

        // Create header
        let headerTR = ['Id', 'Brand', 'Model', 'OS', 'Color', 'Year', 'Price', 'Buy'];

		// Create table
        let tr = document.createElement('tr');
        headerTR.forEach((header)=> {
            let th = document.createElement('th');
            th.textContent = header;
            tr.appendChild(th);
        })
        tableBody.appendChild(tr);

		// Create each value of the mobile table
        data.forEach((value) => {
            tr = document.createElement('tr');
            
            let td = document.createElement('td');
            td.textContent = value.id;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.brand;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.model;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.os;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.color;
            tr.appendChild(td);
            
            td = document.createElement('td');
            td.textContent = value.year;
            tr.appendChild(td);
            
            td = document.createElement('td');
            td.textContent = value.price;
            tr.appendChild(td);

            td = document.createElement('td');
            td.innerHTML = "<button type=\"button\" value=\" "+ value.id + "\" onclick=\"addToShoppingCart("+ value.id + ");\" class=\"btn btn-primary btn-sm\">Buy</button>"
            tr.appendChild(td);
            tableBody.appendChild(tr);
        });

		// Append element to table
        htmlTable.appendChild(table);
    })
}

// Generet shopping cart list
function generateShoppingCart(){
	// Get actual user cookie
	var client = getCookie('actual_user');
	
	// Get list of shopping cart items POST request
    fetchAsyncPost('http://localhost:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/shoppingcart/list', 'POST', client).then( (data) => {
        // Get elements of shopping cart table
        let htmlTable = document.getElementById('mobiles_my_sc_table');
        htmlTable.innerHTML = "";
        let table = document.createElement('table');
        let tableBody = document.createElement('tbody');
        table.appendChild(tableBody);

        // Create header
        let headerTR = ['Id', 'Brand', 'Model', 'OS', 'Color', 'Year', 'Price', 'Buy'];

		// Create table
        let tr = document.createElement('tr');
        headerTR.forEach((header)=> {
            let th = document.createElement('th');
            th.textContent = header;
            tr.appendChild(th);
        })
        tableBody.appendChild(tr);

		// Create each value of the shopping cart table
        data.forEach((value) => {
            tr = document.createElement('tr');
            
            let td = document.createElement('td');
            td.textContent = value.id;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.brand;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.model;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.os;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.color;
            tr.appendChild(td);
            
            td = document.createElement('td');
            td.textContent = value.year;
            tr.appendChild(td);
            
            td = document.createElement('td');
            td.textContent = value.price;
            tr.appendChild(td);

            td = document.createElement('td');
            td.innerHTML = "<button type=\"button\" value=\" "+ value.id + "\" onclick=\"removeShoppingCart("+ value.id + ");\" class=\"btn btn-primary btn-sm\">Quit</button>"
            tr.appendChild(td);
            tableBody.appendChild(tr);
        });

		// Append element to table
        htmlTable.appendChild(table);
    })
}

// Event listener to shopping cart button
formButtonShoppingCart.addEventListener('submit', (event) => {
    event.preventDefault();

	// Update shopping cart
    generateShoppingCart();
    
    // Make shopping cart list visible
	listShoppingCartVis.classList.add("visuallyvisible");
    listShoppingCartVis.classList.remove("visuallyhidden");
    
    // Hide mobiles list
    listMobilesVis.classList.add("visuallyhidden");
    listMobilesVis.classList.remove("visuallyvisible");
	
	// Hide shopping cart and logout buttons
	shoppingCartVis.classList.add("visuallyhidden");
	shoppingCartVis.classList.remove("visuallyvisible");
	clientLogoutVis.classList.add("visuallyhidden");
	clientLogoutVis.classList.remove("visuallyvisible");
});

// Generate mobiles table
function generateMobilesAdmin(){
	// Get list of mobiles GET request
    fetchAsync('http://localhost:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/mobiles', 'GET').then( (data) => {
        // Get list of mobiles table
        let htmlTable = document.getElementById('mobiles_sc_table');
        htmlTable.innerHTML = "";
        let table = document.createElement('table');
        let tableBody = document.createElement('tbody');
        table.appendChild(tableBody);

		// Create header
        let headerTR = ['Id', 'Brand', 'Model', 'OS', 'Color', 'Year', 'Price', 'Buy'];

		// Create table
        let tr = document.createElement('tr');
        headerTR.forEach((header)=> {
            let th = document.createElement('th');
            th.textContent = header;
            tr.appendChild(th);
        })
        tableBody.appendChild(tr);

		// Create each value of the mobiles table
        data.forEach((value) => {
            tr = document.createElement('tr');
            
            let td = document.createElement('td');
            td.textContent = value.id;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.brand;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.model;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.os;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.color;
            tr.appendChild(td);
            
            td = document.createElement('td');
            td.textContent = value.year;
            tr.appendChild(td);
            
            td = document.createElement('td');
            td.textContent = value.price;
            tr.appendChild(td);

            td = document.createElement('td');
            td.innerHTML = "<button type=\"button\" value=\" "+ value.id + "\" onclick=\"removeMobile("+ value.id + ");\" class=\"btn btn-primary btn-sm\">Remove</button>"
            tr.appendChild(td);
            tableBody.appendChild(tr);
        });

		// Append elements to table
        htmlTable.appendChild(table);
    })
}