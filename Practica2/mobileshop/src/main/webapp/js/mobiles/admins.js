// Event listener to Register Admin button
formRegisterAdmin.addEventListener('submit', (event) => {
    event.preventDefault();
	
	// Register admin
    registerAdmin(
        document.querySelector('#email3').value,
        document.querySelector('#password3').value
    )
});

// Event listener to Login Admin button
formLoginAdmin.addEventListener('submit', (event) => {
    event.preventDefault();

	// Login admin
    loginAdmin(
        document.querySelector('#email4').value,
        document.querySelector('#password4').value
    )
});

// Event listener to Logout Admin button
formButtonLogoutAdmin.addEventListener('submit', (event) => {
    event.preventDefault();

	// Make visible Register and Login buttons
    formButtonRegisterVis.classList.add("visuallyvisible");
	formButtonRegisterVis.classList.remove("visuallyhidden");
    formButtonLoginVis.classList.add("visuallyvisible");
    formButtonLoginVis.classList.remove("visuallyhidden");

	// Hide list of mobiles
	listMobilesAdminVis.classList.add("visuallyhidden");
    listMobilesAdminVis.classList.remove("visuallyvisible");
	
	// Hide logout button
	adminLogoutVis.classList.add("visuallyhidden");
	adminLogoutVis.classList.remove("visuallyvisible");
	
	// Erase actual user cookie
	eraseCookie('actual_user');
});

// Register admin
async function registerAdmin(email, password){
	// Register admin POST request
    let response = await fetch('http://localhost:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/admins/register',{
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({email, password})
    }).then((resp)=>{
		// Status Ok
        if(resp.status == 201){
        	// Hide Register form
		    adminRegisterVis.classList.add("visuallyhidden");
			adminRegisterVis.classList.remove("visuallyvisible");

			// Make visible register and login
		    formButtonRegisterVis.classList.add("visuallyvisible");
			formButtonRegisterVis.classList.remove("visuallyhidden");
		    formButtonLoginVis.classList.add("visuallyvisible");
		    formButtonLoginVis.classList.remove("visuallyhidden");
		}
    }).catch((err)=>{
        console.log(err);
    });
}

// Login admin
async function loginAdmin(email, password){
	// Login admin POST request
    let response = await fetch('http://localhost:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/admins/login',{
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({email, password})
    }).then((resp)=>{
        // Status Ok
        if(resp.status == 201){
        	// Update list of mobiles
        	generateMobilesAdmin();
        	
        	// Make list of mobiles visible
		    listMobilesAdminVis.classList.add("visuallyvisible");
			listMobilesAdminVis.classList.remove("visuallyhidden");
			
			// Make logout button visible
			adminLogoutVis.classList.add("visuallyvisible");
			adminLogoutVis.classList.remove("visuallyhidden");
			
			// Hide admin login
		    adminLoginVis.classList.add("visuallyhidden");
			adminLoginVis.classList.remove("visuallyvisible");
			
			// Set actual user cookie
			setCookie('actual_user',email,2);
		}
    }).catch((err)=>{
        console.log(err);
    });
}