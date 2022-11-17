var app = new Vue({
    el:"#app",
    data:{
        paymentsFeesObject : {},
        paymentsFees: [],
        selectedPayments : 0,
        createdToken: false,
        token: '',
        id: '',
        payments : [1, 3, 6, 12],
        optCardType: 'debit',
        cardNumberCredit: '',
        numberCardDebit: '2222-3333-4444-5555',
        cardHolder: 'Tomas Quinteros',
        description: 'Pagando netflix2',
        amount: '2000',
        cvv: '123',
        thruDate: '2027-09',
        loanTypes: [],
        errorToats: null,
        errorMsg: null,
    },
    methods:{
        getFeeData: function(){
            axios.post("/api/clients/current/creditCards/fees", {"amount": this.amount, "payments": this.payments})//""
                .then((response) => {

                    this.paymentsFees = response.data;
                    var listString = "";
                    this.paymentsFeesObject = {};
                    var paymentsFeesString = []
                    for(let fee in this.paymentsFees){
                        listString = fee + " cuotas : $" + this.paymentsFees[fee];
                        this.paymentsFeesObject[fee] = listString;
                    }

                })
                .catch((error) => {
                    // handle error
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        postCardData: function(){
            const urlParams = new URLSearchParams(window.location.search);
            const id = urlParams.get('id');
            axios.post(`/api/clients/current/accounts/`,`title=${this.title}&question=${this.question}&tags=${this.tags}`)
                .then((response) => {
                    //get client ifo
                    this.accountInfo = response.data;
                    this.accountInfo.transactions.sort((a,b) => parseInt(b.id - a.id))
                })
                .catch((error) => {
                    // handle error
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function(date){
            return new Date(date).toLocaleDateString('en-gb');
        },
        signOut: function(){
            axios.post('/api/logout')
                .then(response => window.location.href="/web/index.html")
                .catch(() =>{
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
        changedPayment: function(event){
            this.selectedPayments = event.target.value;
            //this.paymentsList = this.loanTypes.find(loanType => loanType.id == this.loanTypeId).payments;
        },

        createCreditCardTransaction: function(event){
            event.preventDefault();
            axios.post('/api/clients/current/creditCards/pay',
            `cardNumberCredit=${this.cardNumberCredit}&cardHolder=${this.cardHolder}&description=${this.description}
            &amount=${this.amount}&cvv=${this.cvv}&payments=${this.selectedPayments}&thruDate=${this.thruDate}`)
                .then(response => {
                    this.createdToken = true;
                    this.id = response.data;
                })
                .catch((error) =>{
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                })
           },

        mostrarExito: function(){
            console.log("lance la funcion mostrame el modal!!!")
            window.alert("muy bien bro");

//            Y vuelvo a payments
            window.location.href="/web/payments.html"
        },

      confirmCreditCardTransaction: function(event){
          event.preventDefault();
          axios.post('/api/clients/current/creditCards/confirm',
          `id=${this.id}&token=${this.token}`)
              .then(response => {
                  console.log("TOKEN RECIBIDO");
                  console.log(response);
                  mostrarExito();

              })
              .catch((error) =>{
                  this.errorMsg = error.response.data;
                  this.errorToats.show();
              })
         },

        createDebitCardTransaction: function(event){
            event.preventDefault();
            axios.post('/api/clients/current/debitCards/pay',
            `numberCardDebit=${this.numberCardDebit}&cardHolder=${this.cardHolder}&description=${this.description}
            &amount=${this.amount}&cvv=${this.cvv}&thruDate=${this.thruDate}`)
                .then(response => {
                    this.createdToken = true;
                    this.id = response.data;
                })
                .catch((error) =>{
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                })
       },




        confirmDebitCardTransaction: function(event){
           event.preventDefault();
           axios.post('/api/clients/current/debitCards/confirm',
           `id=${this.id}&token=${this.token}`)
               .then(response => {
                   console.log("TOKEN RECIBIDO");
                   mostrarExito();

               })
               .catch((error) =>{
                   this.errorMsg = error.response.data;
                   this.errorToats.show();
               })
          },


    },
    mounted: function(){
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        //getFeeData();
    }
})
