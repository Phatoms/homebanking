var app = new Vue({
    el:"#app",
    data:{
        errorToats: null,
        errorMsg: null,
        clientAccounts:[],
        cardType:'none',
        cardColor:'none',
        accountNumber:"VIN",
        maxLimit:0
    },
    methods:{
        formatDate: function(date){
            return new Date(date).toLocaleDateString('en-gb');
        },
        getAccountData: function(){
            axios.get("/api/clients/current/accounts")
            .then((response) => {
                //get client ifo
                this.clientAccounts = response.data;
            })
        },
        signOut: function(){
            axios.post('/api/logout')
            .then(response => window.location.href="/web/index.html")
            .catch(() =>{
                this.errorMsg = "Sign out failed"
                this.errorToats.show();
            })
        },
        create: function(event){
            event.preventDefault();
            if(this.cardType == 'none' || this.cardColor == 'none'){
                this.errorMsg = "You must select a card type and color";
                this.errorToats.show();
            }else if(this.cardType == 'CREDIT' && this.maxLimit <= 0){
                this.errorMsg = "You must insert a greater limit";
                this.errorToats.show();
            }else if(this.cardType == 'DEBIT' && this.accountNumber == "VIN"){
                 this.errorMsg = "Please select an account";
                 this.errorToats.show();
            }else{
                let config = {
                    headers: {
                        'content-type': 'application/x-www-form-urlencoded'
                    }
                }
                if(this.cardType == 'CREDIT'){
                    axios.post(`http://localhost:8080/api/clients/current/creditCards?cardColor=${this.cardColor}&maxLimit=${this.maxLimit}`)
                        .then(response => window.location.href = "/web/cards.html")
                        .catch((error) =>{
                            this.errorMsg = error.response.data;
                            this.errorToats.show();
                        })
                }else{
                    this.errorMsg = "aaaaaaaaaaaaaaaaaaaaaaaaaa";
                    this.errorToats.show();
                    axios.post(`http://localhost:8080/api/clients/current/debitCards?accountNumber=${this.accountNumber}&cardColor=${this.cardColor}`)
                        .then(response => window.location.href = "/web/cards.html")
                        .catch((error) =>{
                            this.errorMsg = error.response.data;
                            this.errorToats.show();
                        })
                }

            }
        },
    },
    mounted: function(){
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getAccountData();
    }
})