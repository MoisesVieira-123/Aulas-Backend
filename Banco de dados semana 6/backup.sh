#!/usr/bin/env bash

set -Eeuo pipefail
umask 077

# Configurações do banco de dados
DB_NAME="${DB_NAME:-ecommerce}"
DB_USER="${DB_USER:-postgres}"

# Diretório onde os backups serão armazenados
BACKUP_DIR="${BACKUP_DIR:-$HOME/backups/postgresql}"

# Tempo de retenção dos backups
RETENTION_DAYS="${RETENTION_DAYS:-30}"

mkdir -p "$BACKUP_DIR"

# Data e hora para identificar o backup
STAMP="$(date +'%Y-%m-%d_%H-%M-%S')"

BACKUP_FILE="$BACKUP_DIR/${DB_NAME}_${STAMP}.dump"
TEMP_FILE="${BACKUP_FILE}.tmp"
LOG_FILE="$BACKUP_DIR/backup.log"

log() {
    printf '[%s] %s\n' "$(date +'%F %T')" "$*" | tee -a "$LOG_FILE"
}

cleanup() {
    rm -f "$TEMP_FILE"
}

trap cleanup EXIT

# Verifica se pg_dump está instalado
if ! command -v pg_dump >/dev/null 2>&1; then
    log "ERRO: pg_dump não foi encontrado."
    exit 127
fi

log "Iniciando backup do banco '$DB_NAME'."

# Realiza o backup em formato customizado
if pg_dump \
    --username="$DB_USER" \
    --dbname="$DB_NAME" \
    --format=custom \
    --file="$TEMP_FILE"; then

    # Só publica o arquivo final se o backup terminar corretamente
    mv "$TEMP_FILE" "$BACKUP_FILE"

    log "Backup concluído: $BACKUP_FILE"

else
    log "ERRO: falha ao realizar o backup."
    exit 1
fi

# Remove backups antigos, respeitando a retenção configurada
find "$BACKUP_DIR" \
    -maxdepth 1 \
    -type f \
    -name "${DB_NAME}_*.dump" \
    -mtime "+$((RETENTION_DAYS - 1))" \
    -print \
    -delete |
while IFS= read -r removed; do
    log "Backup antigo removido: $removed"
done

log "Rotina de backup finalizada."
