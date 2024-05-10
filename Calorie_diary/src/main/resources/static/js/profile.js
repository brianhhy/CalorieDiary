/*active button class onclick*/
const toggleBtn = document.querySelector('.navbar__toggleBtn');
const menu = document.querySelector('.navbar__menu');
const icons = document.querySelector('.navbar__icons');

toggleBtn.addEventListener('click',()=>{
    menu.classList.toggle('active');
    icons.classList.toggle('active');
});

$('nav a').click(function(e) {
    e.preventDefault();
    $('nav a').removeClass('active');
    $(this).addClass('active');
    if(this.id === !'payment'){
      $('.payment').addClass('noshow');
    }
    else if(this.id === 'payment') {
      $('.payment').removeClass('noshow');
      $('.rightbox').children().not('.payment').addClass('noshow');
    }
    else if (this.id === 'profile') {
      $('.profile').removeClass('noshow');
       $('.rightbox').children().not('.profile').addClass('noshow');
    }
    else if(this.id === 'subscription') {
      $('.subscription').removeClass('noshow');
      $('.rightbox').children().not('.subscription').addClass('noshow');
    }
      else if(this.id === 'privacy') {
      $('.privacy').removeClass('noshow');
      $('.rightbox').children().not('.privacy').addClass('noshow');
    }
    else if(this.id === 'settings') {
      $('.settings').removeClass('noshow');
      $('.rightbox').children().not('.settings').addClass('noshow');
    }
  });