document.addEventListener('DOMContentLoaded', function() {
    // Verifica se o usuário está logado
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user || !user.id) {
        window.location.href = 'index.html';
        return;
    }

    // Atualiza a mensagem de boas-vindas
    document.getElementById('welcome-message').textContent += user.name;

    // Elementos da interface
    const songsList = document.getElementById('songs-list');
    const searchInput = document.getElementById('search-input');
    const searchBtn = document.getElementById('search-btn');
    const logoutBtn = document.getElementById('logout-btn');
    const addSongBtn = document.getElementById('add-song-btn');

    // Carrega as músicas
    loadSongs();

    // Event listeners
    searchBtn.addEventListener('click', () => {
        const searchTerm = searchInput.value.trim();
        loadSongs(searchTerm);
    });

    searchInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            const searchTerm = searchInput.value.trim();
            loadSongs(searchTerm);
        }
    });

    logoutBtn.addEventListener('click', logout);
    addSongBtn.addEventListener('click', () => {
        alert('Funcionalidade de adicionar música será implementada em breve!');
    });

    // Função para carregar músicas
    function loadSongs(searchTerm = '') {
        let url = 'http://localhost:8080/song';
        if (searchTerm) {
            url += `?title=${encodeURIComponent(searchTerm)}`;
        }

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao carregar músicas');
                }
                return response.json();
            })
            .then(songs => {
                renderSongs(songs);
            })
            .catch(error => {
                console.error('Erro:', error);
                songsList.innerHTML = '<div class="no-results">Erro ao carregar músicas. Tente novamente.</div>';
            });
    }

    // Função para renderizar as músicas
    function renderSongs(songs) {
        if (!songs || songs.length === 0) {
            songsList.innerHTML = '<div class="no-results">Nenhuma música encontrada.</div>';
            return;
        }

        songsList.innerHTML = '';
        songs.forEach(song => {
            const songCard = document.createElement('div');
            songCard.className = 'song-card';
            
            // Média das avaliações
            const avgRating = song.ratings.length > 0 
                ? (song.ratings.reduce((sum, rating) => sum + rating.score, 0) / song.ratings.length).toFixed(1)
                : 0;

            songCard.innerHTML = `
                <div class="song-header">
                    <div class="song-title">${song.title}</div>
                    <div class="song-rating">${renderStars(avgRating)}</div>
                </div>
                <div class="song-artist">${song.artistName}</div>
                <div class="song-details">
                    <span>${song.genre}</span> • <span>${song.releaseYear}</span>
                </div>
                
                <div class="rating-container">
                    <div class="rating-header">
                        <div class="rating-stars">${renderStars(avgRating)}</div>
                        <div class="rating-count">${song.ratings.length} avaliação(s)</div>
                    </div>
                    
                    <div class="rating-list">
                        ${song.ratings.length > 0 
                            ? song.ratings.map(rating => `
                                <div class="rating-item">
                                    <div class="rating-user">${rating.userId === user.id ? 'Você' : 'Usuário'}</div>
                                    <div class="rating-comment">${rating.review || 'Sem comentário'}</div>
                                    <div class="rating-meta">
                                        <span>${renderStars(rating.score)}</span>
                                        <span>${new Date(rating.ratedAt).toLocaleDateString()}</span>
                                    </div>
                                </div>
                            `).join('') 
                            : '<div class="no-ratings">Nenhuma avaliação ainda</div>'}
                    </div>
                    
                    <div class="add-rating-form">
                        <h4>Avaliar esta música</h4>
                        <div class="star-rating" data-song-id="${song.id}">
                            <span class="star" data-value="1"><i class="fas fa-star"></i></span>
                            <span class="star" data-value="2"><i class="fas fa-star"></i></span>
                            <span class="star" data-value="3"><i class="fas fa-star"></i></span>
                            <span class="star" data-value="4"><i class="fas fa-star"></i></span>
                            <span class="star" data-value="5"><i class="fas fa-star"></i></span>
                        </div>
                        <textarea id="review-${song.id}" placeholder="Deixe seu comentário (opcional)"></textarea>
                        <button class="btn btn-primary" onclick="submitRating('${song.id}')">Enviar Avaliação</button>
                    </div>
                </div>
            `;
            
            songsList.appendChild(songCard);
        });

        // Adiciona eventos para as estrelas de avaliação
        document.querySelectorAll('.star-rating .star').forEach(star => {
            star.addEventListener('click', function() {
                const container = this.parentElement;
                const value = parseInt(this.getAttribute('data-value'));
                
                // Atualiza a visualização das estrelas
                container.querySelectorAll('.star').forEach((s, index) => {
                    if (index < value) {
                        s.classList.add('active');
                    } else {
                        s.classList.remove('active');
                    }
                });
                
                // Armazena a avaliação selecionada
                container.setAttribute('data-rating', value);
            });
        });
    }

    // Função para renderizar estrelas baseado na avaliação
    function renderStars(rating) {
        const fullStars = Math.floor(rating);
        const hasHalfStar = rating % 1 >= 0.5;
        let stars = '';
        
        for (let i = 1; i <= 5; i++) {
            if (i <= fullStars) {
                stars += '<i class="fas fa-star"></i>';
            } else if (i === fullStars + 1 && hasHalfStar) {
                stars += '<i class="fas fa-star-half-alt"></i>';
            } else {
                stars += '<i class="far fa-star"></i>';
            }
        }
        
        return stars;
    }

    // Função para enviar avaliação (disponível no escopo global)
    window.submitRating = function(songId) {
        const ratingContainer = document.querySelector(`.star-rating[data-song-id="${songId}"]`);
        const selectedRating = ratingContainer.getAttribute('data-rating');
        const reviewText = document.getElementById(`review-${songId}`).value.trim();
        
        if (!selectedRating) {
            alert('Por favor, selecione uma avaliação com as estrelas');
            return;
        }
        
        const ratingData = {
            userId: user.id,
            songId: songId,
            score: parseInt(selectedRating),
            review: reviewText || null
        };
        
        fetch('http://localhost:8080/rating', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(ratingData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao enviar avaliação');
            }
            return response.json();
        })
        .then(data => {
            alert('Avaliação enviada com sucesso!');
            loadSongs(searchInput.value.trim()); // Recarrega as músicas
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao enviar avaliação. Tente novamente.');
        });
    };

    // Função para logout
    function logout() {
        localStorage.removeItem('user');
        window.location.href = 'index.html';
    }
});