const formButtonRegisterClient = document.getElementById('formButtonRegisterClient');
const formButtonLoginClient = document.getElementById('formButtonLoginClient');
const formButtonRegisterAdmin = document.getElementById('formButtonRegisterAdmin');
const formButtonLoginAdmin = document.getElementById('formButtonLoginAdmin');
const formAddMobile = document.getElementById('formAddMobile');
const formButtonShoppingCart = document.getElementById('formButtonShoppingCart');
const listShoppingCartVis = document.getElementById('mobile_my_sc_list_visible');
const formButtonExitCart = document.getElementById('formButtonExitCart');
const formRegisterClient = document.getElementById('formRegisterClient');
const formLoginClient = document.getElementById('formLoginClient');
const clientRegisterVis = document.getElementById('client_register_visible');
const clientLoginVis = document.getElementById('client_login_visible');
const adminRegisterVis = document.getElementById('admin_register_visible');
const adminLoginVis = document.getElementById('admin_login_visible');
const formButtonRegisterVis = document.getElementById('select_option_reg_visible');
const formButtonLoginVis = document.getElementById('select_option_log_visible');
const listMobilesVis = document.getElementById('mobile_list_visible');
const listMobilesAdminVis = document.getElementById('mobile_sc_list_visible');
const shoppingCartVis = document.getElementById('see_shopping_cart_visible');
const clientLogoutVis = document.getElementById('logout_cli_visible');
const adminLogoutVis = document.getElementById('logout_adm_visible');
const formButtonLogoutClient = document.getElementById('formButtonLogoutClient');
const formButtonLogoutAdmin = document.getElementById('formButtonLogoutAdmin');

// Event listener to Register Client button
formButtonRegisterClient.addEventListener('submit', (event) => {
    event.preventDefault();
    
    // Make Register client form visible
    clientRegisterVis.classList.add("visuallyvisible");
    clientRegisterVis.classList.remove("visuallyhidden");

	// Hide Register and Login buttons
	formButtonRegisterVis.classList.add("visuallyhidden");
    formButtonRegisterVis.classList.remove("visuallyvisible");
    formButtonLoginVis.classList.add("visuallyhidden");
    formButtonLoginVis.classList.remove("visuallyvisible");
});

// Event listener to Login Client button
formButtonLoginClient.addEventListener('submit', (event) => {
    event.preventDefault();
    
    // Make Login client form visible
    clientLoginVis.classList.add("visuallyvisible");
    clientLoginVis.classList.remove("visuallyhidden");

	// Hide Register and Login buttons
	formButtonRegisterVis.classList.add("visuallyhidden");
    formButtonRegisterVis.classList.remove("visuallyvisible");
    formButtonLoginVis.classList.add("visuallyhidden");
    formButtonLoginVis.classList.remove("visuallyvisible");
});

// Event listener to Register Admin button
formButtonRegisterAdmin.addEventListener('submit', (event) => {
    event.preventDefault();
    
    // Make Register admin form visible
    adminRegisterVis.classList.add("visuallyvisible");
    adminRegisterVis.classList.remove("visuallyhidden");

	// Hide Register and Login buttons
	formButtonRegisterVis.classList.add("visuallyhidden");
    formButtonRegisterVis.classList.remove("visuallyvisible");
    formButtonLoginVis.classList.add("visuallyhidden");
    formButtonLoginVis.classList.remove("visuallyvisible");
});

// Event listener to Loginc Admin button
formButtonLoginAdmin.addEventListener('submit', (event) => {
    event.preventDefault();
    
    // Make Login admin form visible
    adminLoginVis.classList.add("visuallyvisible");
    adminLoginVis.classList.remove("visuallyhidden");

	// Hide Register and Login buttons
	formButtonRegisterVis.classList.add("visuallyhidden");
    formButtonRegisterVis.classList.remove("visuallyvisible");
    formButtonLoginVis.classList.add("visuallyhidden");
    formButtonLoginVis.classList.remove("visuallyvisible");
});

var map;
function initMap() {
	map = new google.maps.Map(document.getElementById('map'), {
		center: {lat: 37.186640511382585, lng: -3.6060287047237893},
		zoom: 13
	});
	var marker = new google.maps.Marker({
		position: {lat: 37.186640511382585, lng: -3.6060287047237893},
		map: map,
		title: 'Mobile Store'
	});
}