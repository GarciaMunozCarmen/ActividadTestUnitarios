package es.fplumara.dam1.coche;

public class CocheService {

    private CocheRepository cocheRepository;

    public CocheService(CocheRepository cocheRepository) {
        this.cocheRepository = cocheRepository;
    }

    /**
     * Comprueba que la matricula tiene un formato adecuado (4 números y 3 letras en mayúsculas)
     * 1234ABC
     * 0000ZZZ
     **/
    public boolean validaMatricula(String matricula) {
        if(matricula == null || matricula.isEmpty()){
            return false;
        }
        return matricula.matches("\\d{4}[A-Z]{3}");
    }

    public void comprarCoche(Coche coche) {
        if (!validaMatricula(coche.getMatricula())) {
            throw new IllegalArgumentException("Matrícula invalida");
        }
        cocheRepository.save(coche);
    }

    public Coche buscarCoche(String matricula) {
        if (!validaMatricula(matricula)) {
            throw new IllegalArgumentException("Matrícula invalida");
        }
        return cocheRepository.findByMatricula(matricula);
    }
}
