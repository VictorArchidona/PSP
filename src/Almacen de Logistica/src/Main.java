public class Main {
    // --- MAIN PARA PROBARLO ---
    public static void main(String[] args) {
        Almacen almacen = new Almacen();

        // HILO PRODUCTOR (CAMIÓN)
        Thread camion = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                almacen.entregarCaja(i);
                try { Thread.sleep(500); } catch (Exception e){}
            }
        });

        // HILO CONSUMIDOR (OPERARIO)
        Thread operario = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                almacen.recogerCaja();
                try { Thread.sleep(1000); } catch (Exception e){} // Es más lento
            }
        });

        camion.start();
        operario.start();

        try {
            // El Main se espera aquí a que el Camión termine sus 10 viajes
            camion.join();

            // Y espera a que el Operario termine de recoger todo
            operario.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Este mensaje ahora sí saldrá AL FINAL de verdad
        System.out.println("\n--- FIN DE LA SIMULACIÓN: Todo el trabajo terminado ---");
    }
}

