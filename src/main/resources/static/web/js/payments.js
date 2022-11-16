var app = new Vue({
    el:"#app",
    data:{
        createdToken: false,
        token: '',
        optCardType: 'credit',
        cardNumberCredit: '',
        numberCardDebit: '',
        cardHolder: '',
        description: '',
        amount: '',
        cvv: '',
        thruDate: '',
        loanTypes: [],
        errorToats: null,
        errorMsg: null,
    },
    methods:{
        getData: function(){
            const urlParams = new URLSearchParams(window.location.search);
            const id = urlParams.get('id');
            axios.get(`/api/clients/current/accounts/${id}`)
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
        changedType: function(){
            this.paymentsList = this.loanTypes.find(loanType => loanType.id == this.loanTypeId).payments;
        },
        createDebitCardTransaction: function(event){

//            this.createdToken = true;
            console.log(this.createdToken);
            event.preventDefault();
            axios.post('/api/clients/current/debitCards/pagar',
            `numberCardDebit=${this.numberCardDebit}&cardHolder=${this.cardHolder}&description=${this.description}
            &amount=${this.amount}&cvv=${this.cvv}&thruDate=${this.thruDate}`)
                .then(response => {
                    this.createdToken = true;

                })
                .catch((error) =>{
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                })
           }
    },
    mounted: function(){
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();
    }
})
