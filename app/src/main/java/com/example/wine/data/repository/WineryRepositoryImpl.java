package com.example.wine.data.repository;


import android.content.Context;

import com.example.wine.data.local.AppDatabase;
import com.example.wine.data.local.AppDatabaseProvider; // Use o provedor Singleton
import com.example.wine.data.local.dao.WineryDao;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.domain.model.Winery;
import com.example.wine.domain.repository.WineryRepository;
import com.example.wine.domain.callback.RepositoryCallback; // <-- Verifique se o caminho está correto
import com.example.wine.utils.Mapper; // Assume que esta classe existe

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class WineryRepositoryImpl implements WineryRepository {

    private final WineryDao wineryDao;
    // Pool de threads para executar as operações do DB em background
    private final ExecutorService executorService;

    // Construtor recebe o contexto para obter o DB Singleton e o DAO
    public WineryRepositoryImpl(Context context) {
        // Obtenha a instância Singleton do banco de dados
        AppDatabase database = AppDatabaseProvider.getDatabase(context);
        this.wineryDao = database.wineryDao();
        // Crie um Executor para tarefas em background. Um fixed thread pool é comum.
        this.executorService = Executors.newFixedThreadPool(4); // Exemplo com 4 threads
    }

    // Método helper para mapear Entity -> Domain Model
    private Winery mapEntityToDomain(WineryEntity entity) {
        if (entity == null) return null;
        return new Winery(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getCity(),
                entity.getCountry(),
                entity.getLatitude(),
                entity.getLongitude()
        );
    }

    // Método helper para mapear Domain Model -> Entity
    private WineryEntity mapDomainToEntity(Winery domain) {
        if (domain == null) return null;
        WineryEntity entity = new WineryEntity();
        // Copia o ID APENAS SE FOR UM OBJETO EXISTENTE (para update/delete)
        // Room ignora o ID auto-gerado na inserção se ele for 0 ou não for definido.
        if (domain.getId() != 0) { // Verifique se o ID foi definido no modelo de domínio
            entity.setId(domain.getId());
        }
        entity.setName(domain.getName());
        entity.setAddress(domain.getAddress());
        entity.setCity(domain.getCity());
        entity.setCountry(domain.getCountry());
        entity.setLatitude(domain.getLatitude());
        entity.setLongitude(domain.getLongitude());
        return entity;
    }


    // MÉTODO SAVE ASSÍNCRONO - CORRIGIDO (removido o duplicado)
    @Override
    public void save(Winery winery, RepositoryCallback<Long> callback) {
        WineryEntity entity = Mapper.toEntity(winery); // Use seu Mapper
        // Envia a tarefa para ser executada em uma thread do pool
        executorService.execute(() -> {
            try {
                long newId = wineryDao.insert(entity);
                // Notifica o sucesso através do callback
                if (callback != null) {
                    // IMPORTANTE: Se o callback atualizar a UI, use runOnUiThread na Activity
                    // ou um Handler para garantir que rode na thread principal.
                    callback.onSuccess(newId);
                }
            } catch (Exception e) {
                // Notifica o erro através do callback
                if (callback != null) {
                    // IMPORTANTE: Se o callback atualizar a UI, use runOnUiThread na Activity
                    // ou um Handler para garantir que rode na thread principal.
                    callback.onError(e);
                }
            }
        });
    }

    // Métodos getAll, getById, update, delete ainda são síncronos para a camada que chama o Repositório.
    // Para uma implementação completamente saudável, eles também deveriam ser assíncronos.

    @Override
    public List<Winery> getAll() {
        // Esta chamada ainda BLOQUEIA A THREAD QUE CHAMA repository.getAll()
        // Deveria ser assíncrono, ex: void getAll(RepositoryCallback<List<Winery>> callback);
        // Ou retornar LiveData<List<Winery>> se usar ViewModel.
        List<WineryEntity> entities = wineryDao.getAllWineries();
        return entities.stream()
                .map(Mapper::toDomain) // Use seu Mapper
                .collect(Collectors.toList());
    }

    @Override
    public Winery getById(int id) {
        // Esta chamada ainda BLOQUEIA A THREAD QUE CHAMA repository.getById()
        // Deveria ser assíncrono, ex: void getById(int id, RepositoryCallback<Winery> callback);
        // Ou retornar LiveData<Winery> se usar ViewModel.
        WineryEntity entity = wineryDao.getById(id);
        return entity != null ? Mapper.toDomain(entity) : null; // Use seu Mapper
    }

    @Override
    public int update(Winery winery) {
        // Esta chamada ainda BLOQUEIA A THREAD QUE CHAMA repository.update()
        // Deveria ser assíncrono, ex: void update(Winery winery, RepositoryCallback<Integer> callback);
        WineryEntity entity = Mapper.toEntity(winery); // Use seu Mapper
        return wineryDao.update(entity);
    }

    @Override
    public int delete(int id) {
        // Esta chamada ainda BLOQUEIA A THREAD QUE CHAMA repository.delete()
        // Deveria ser assíncrono, ex: void delete(int id, RepositoryCallback<Integer> callback);
        WineryEntity entity = wineryDao.getById(id); // Primeiro busca (bloqueia)
        if (entity != null) {
            return wineryDao.delete(entity); // Depois deleta (bloqueia)
        }
        return 0;
    }

    // Método para encerrar o pool de threads quando não for mais necessário
    // Isso pode ser feito na Application class ou em um escopo de ciclo de vida apropriado
    public void shutdown() {
        executorService.shutdown();
    }
}