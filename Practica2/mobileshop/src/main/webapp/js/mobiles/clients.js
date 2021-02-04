// Set cookie function
function setCookie(name,value,days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}

// Get cookie function
function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

// Erase cookie function
function eraseCookie(name) {   
    document.cookie = name +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

// Event listener to Register Client button
formRegisterClient.addEventListener('submit', (event) => {
    event.preventDefault();

	// Register client
    registerClient(
        document.querySelector('#email').value,
        document.querySelector('#password').value
    )
});

// Event listener to Login Client button
formLoginClient.addEventListener('submit', (event) => {
    event.preventDefault();

	// Login client
    loginClient(
        document.querySelector('#email2').value,
        document.querySelector('#password2').value
    )
});

// Event listener to Logout Client button
formButtonLogoutClient.addEventListener('submit', (event) => {
    event.preventDefault();

	// Make Register and Login buttons visible
    formButtonRegisterVis.classList.add("visuallyvisible");
	formButtonRegisterVis.classList.remove("visuallyhidden");
    formButtonLoginVis.classList.add("visuallyvisible");
    formButtonLoginVis.classList.remove("visuallyhidden");

	// Hide list of mobiles
	listMobilesVis.classList.add("visuallyhidden");
    listMobilesVis.classList.remove("visuallyvisible");
	
	// Hide Shopping cart and Logout buttons
	shoppingCartVis.classList.add("visuallyhidden");
	shoppingCartVis.classList.remove("visuallyvisible");
	clientLogoutVis.classList.add("visuallyhidden");
	clientLogoutVis.classList.remove("visuallyvisible");
	
	// Erase actual user cookie
	eraseCookie('actual_user');
});

// Register client
async function registerClient(email, password){
	// Register client POST request
    let response = await fetch('http://localhost:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/clients/register',{
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({email, password})
    }).then((resp)=>{
        // Status Ok
        if(resp.status == 201){
        	// Hide register client form
		    clientRegisterVis.classList.add("visuallyhidden");
			clientRegisterVis.classList.remove("visuallyvisible");
		
			// Make visible register and login buttons
		    formButtonRegisterVis.classList.add("visuallyvisible");
			formButtonRegisterVis.classList.remove("visuallyhidden");
		    formButtonLoginVis.classList.add("visuallyvisible");
		    formButtonLoginVis.classList.remove("visuallyhidden");
		}
    }).catch((err)=>{
        console.log(err);
    });
}

// Login client
async function loginClient(email, password){
	// Login client POST request
    let response = await fetch('http://localhost:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/clients/login',{
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
        	generateMobiles();
        	
        	// Make visible mobiles list
		    listMobilesVis.classList.add("visuallyvisible");
			listMobilesVis.classList.remove("visuallyhidden");
			
			// Make shopping cart and client logout button visible
			shoppingCartVis.classList.add("visuallyvisible");
			shoppingCartVis.classList.remove("visuallyhidden");
			clientLogoutVis.classList.add("visuallyvisible");
			clientLogoutVis.classList.remove("visuallyhidden");
			
			// Hide client login form
		    clientLoginVis.classList.add("visuallyhidden");
			clientLoginVis.classList.remove("visuallyvisible");
			
			// Set actual user cookie
			setCookie('actual_user',email,2);
		}
    }).catch((err)=>{
        console.log(err);
    });
}