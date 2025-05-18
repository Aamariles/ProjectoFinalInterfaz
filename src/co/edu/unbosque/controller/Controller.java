package co.edu.unbosque.controller;

import co.edu.unbosque.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ActionListener {
    private LoginFrame loginFrame;
    private PrincipalFrame principalFrame;

    // Referencias a los paneles de contenido principales para gestionarlos
    private PrincipalPanel dashboardContentPanel;
    private TorneosPanel torneosPanel;
    private EquiposPanel equiposPanel; // Nuevo panel para la sección de Equipos
    // private TeamsPanel teamsPanel; // Ejemplo para futuras secciones (nombre antiguo)


    public Controller() {
        loginFrame = new LoginFrame();
        principalFrame = new PrincipalFrame(); // Asume que PrincipalFrame está refactorizado

        attachLoginListeners();
        principalFrame.setVisible(false);
    }

    public void showLogin() {
        loginFrame.setVisible(true);
    }

    private void attachLoginListeners() {
        loginFrame.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginFrame.getLoginPanel() != null &&
                        e.getActionCommand().equals(loginFrame.getLoginPanel().LOGIN_COMMAND)) {
                    handleLoginAttempt();
                } else if (loginFrame.getLoginPanel() == null && e.getActionCommand().equals("LOGIN_SUBMIT")){
                    System.err.println("Advertencia: loginFrame.getLoginPanel() es null, pero se procesa LOGIN_SUBMIT.");
                    handleLoginAttempt();
                } else {
                    System.err.println("Error al obtener LoginPanel o comando desconocido en Login: " + e.getActionCommand());
                }
            }
        });
    }

    private void handleLoginAttempt() {
        String user = loginFrame.getUsername();
        String pass = loginFrame.getPassword();

        if ("admin".equals(user) && "123".equals(pass)) { // Lógica de autenticación
            loginFrame.dispose();
            principalFrame.setVisible(true);
            setupPrincipalFrameNavigation(); // Configurar navegación y vista inicial
        } else {
            loginFrame.showMessage("Credenciales incorrectas. Por favor, inténtelo de nuevo.",
                    "Error de Autenticación",
                    JOptionPane.ERROR_MESSAGE);
            loginFrame.clearFields();
            if (loginFrame.getUsernameField() != null) {
                loginFrame.getUsernameField().requestFocusInWindow();
            }
        }
    }

    private void setupPrincipalFrameNavigation() {
        principalFrame.addNavigationListeners(this); // El Controller maneja todos los eventos de navegación
        showDashboardView(); // Mostrar el dashboard como vista inicial
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        PanelNavegacion navPanel = principalFrame.getNavigationPanel();

        // Validar navPanel para evitar NullPointerException
        if (navPanel == null && (command.equals(PanelNavegacion.NAV_DASHBOARD_COMMAND) ||
                command.equals(PanelNavegacion.NAV_TOURNAMENTS_COMMAND) ||
                command.equals(PanelNavegacion.NAV_TEAMS_COMMAND) ||
                command.equals(PanelNavegacion.NAV_MATCHES_COMMAND) ||
                command.equals(PanelNavegacion.NAV_REPORTS_COMMAND) ||
                command.equals(PanelNavegacion.NAV_SETTINGS_COMMAND))) {
            System.err.println("Panel de Navegación (PanelNavegacion) no encontrado en PrincipalFrame.");
            return;
        }

        boolean comandoDeNavegacionPrincipal = true;

        switch (command) {
            case PanelNavegacion.NAV_DASHBOARD_COMMAND:
                showDashboardView();
                break;
            case PanelNavegacion.NAV_TOURNAMENTS_COMMAND:
                showTorneosView();
                break;
            case PanelNavegacion.NAV_TEAMS_COMMAND:
                showEquiposView(); // Mostrar la nueva vista de Equipos
                break;
            case PanelNavegacion.NAV_MATCHES_COMMAND:
                principalFrame.showMessage("Sección 'Partidas' no implementada todavía.", "En Desarrollo", JOptionPane.INFORMATION_MESSAGE);
                if (navPanel != null) navPanel.setActiveButton(navPanel.getBtnNavMatches());
                break;
            case PanelNavegacion.NAV_REPORTS_COMMAND:
                principalFrame.showMessage("Sección 'Reportes' no implementada todavía.", "En Desarrollo", JOptionPane.INFORMATION_MESSAGE);
                if (navPanel != null) navPanel.setActiveButton(navPanel.getBtnNavReports());
                break;
            case PanelNavegacion.NAV_SETTINGS_COMMAND:
                principalFrame.showMessage("Sección 'Configuración' no implementada todavía.", "En Desarrollo", JOptionPane.INFORMATION_MESSAGE);
                if (navPanel != null) navPanel.setActiveButton(navPanel.getBtnNavSettings());
                break;
            default:
                comandoDeNavegacionPrincipal = false; // No fue un comando de navegación principal
                break;
        }

        if(comandoDeNavegacionPrincipal) return; // Si ya se manejó, salir

        // Manejar otros comandos (específicos de los paneles de contenido)
        if (command.equals(EquiposPrincipalPanel.ACCION_CREAR_EQUIPO)) {
            handleCrearNuevoEquipo();
        } else if (command.equals(EquiposPrincipalPanel.ACCION_BUSCAR_EQUIPOS)) {
            handleBuscarEquipos();
        }
        // Nota: ACCION_VER_DETALLES_EQUIPO se maneja con un callback directamente en EquiposVistaGeneralPanel
        // que llama a equiposPanel.mostrarDetalleEquipo(idEquipo). Si se necesita lógica adicional
        // en el controller para esa acción, se podría ajustar.

        // Aquí puedes añadir más 'else if' para otros action commands de otros paneles
    }

    private void showDashboardView() {
        if (dashboardContentPanel == null) {
            dashboardContentPanel = new PrincipalPanel();
        }
        if (!(principalFrame.getCurrentCenterPanel() instanceof PrincipalPanel)) {
            principalFrame.setCenterPanel(dashboardContentPanel);
        }
        principalFrame.setMainContentTitle("Dashboard");
        PanelNavegacion navPanel = principalFrame.getNavigationPanel();
        if (navPanel != null && navPanel.getBtnNavDashboard() != null) {
            navPanel.setActiveButton(navPanel.getBtnNavDashboard());
        }
        loadDashboardData();
    }

    private void showTorneosView() {
        if (torneosPanel == null) {
            torneosPanel = new TorneosPanel();
        }
        if (!(principalFrame.getCurrentCenterPanel() instanceof TorneosPanel)) {
            principalFrame.setCenterPanel(torneosPanel);
        }
        principalFrame.setMainContentTitle("Torneos");
        PanelNavegacion navPanel = principalFrame.getNavigationPanel();
        if (navPanel != null && navPanel.getBtnNavTournaments() != null) {
            navPanel.setActiveButton(navPanel.getBtnNavTournaments());
        }
        loadTournamentsData();
    }

    private void showEquiposView() {
        if (equiposPanel == null) {
            // EquiposPanel espera un ActionListener general en su constructor
            // para acciones como "Crear Nuevo Equipo" o "Buscar"
            equiposPanel = new EquiposPanel(this);
        }
        if (!(principalFrame.getCurrentCenterPanel() instanceof EquiposPanel)) {
            principalFrame.setCenterPanel(equiposPanel);
        }
        principalFrame.setMainContentTitle("Gestión de Equipos");
        PanelNavegacion navPanel = principalFrame.getNavigationPanel();
        if (navPanel != null && navPanel.getBtnNavTeams() != null) {
            navPanel.setActiveButton(navPanel.getBtnNavTeams());
        }
        loadEquiposOverviewData();
    }

    private void loadDashboardData() {
        System.out.println("Cargando datos del Dashboard...");
        if (dashboardContentPanel != null) {
            dashboardContentPanel.updateSummaryCards("Próximo: Duelo Épico", "7 Activos", "Mega Jugador");
            Object[][] matchesData = {
                    {"Los Imbatibles vs. Los Invencibles", "Mañana 18:00", "Programado"},
                    {"Equipo Delta vs. Equipo Gamma", "Ayer 20:00", "Finalizado (1-3)"}
            };
            dashboardContentPanel.updateRecentMatchesTable(matchesData);
        }
    }

    private void loadTournamentsData() {
        System.out.println("Cargando datos de Torneos...");
        if (torneosPanel != null && torneosPanel.getGeneralInfoPanel() != null) {
            List<Object[]> sampleTournamentData = new ArrayList<>();
            sampleTournamentData.add(new Object[]{"Copa Continental", "En curso", "/imagenes/logo_generic_game.png", "24 equipos", "Global", "Jun 2025 - Sep 2025", true});
            sampleTournamentData.add(new Object[]{"Liga Estelar", "Finalizado", "/imagenes/logo_fifa.png", "16 equipos", "Nacional", "Ene 2025 - Abr 2025", false});

            ActionListener tournamentDetailsListener = ae -> {
                JButton button = (JButton) ae.getSource();
                String tournamentName = "N/A";
                if(button.getClientProperty("tournamentName") != null){ // Chequeo de nulidad
                    tournamentName = button.getClientProperty("tournamentName").toString();
                }
                principalFrame.showMessage("Detalles (Controller): " + tournamentName, "Info Torneo", JOptionPane.INFORMATION_MESSAGE);
            };
            torneosPanel.getGeneralInfoPanel().refreshTournamentCards(sampleTournamentData, tournamentDetailsListener);
        }
    }

    private void loadEquiposOverviewData() {
        System.out.println("Cargando datos para la vista general de equipos...");
        if (equiposPanel != null && equiposPanel.getEquiposPrincipalPanel() != null) {
            List<Object[]> datosFicticios = new ArrayList<>();
            datosFicticios.add(new Object[]{"Águilas Reales", "/imagenes/logo_equipo_1.png", 6, "Ciudad Capital, PA", "Overwatch", "EQ007"});
            datosFicticios.add(new Object[]{"Serpientes Cósmicas", "/imagenes/logo_equipo_2.png", 5, "Metrópolis Norte, EC", "League of Legends", "EQ008"});
            datosFicticios.add(new Object[]{"Robots Invencibles", "/imagenes/logo_equipo_3.png", 7, "Tecnopolis, AR", "Apex Legends", "EQ009"});
            equiposPanel.getEquiposPrincipalPanel().actualizarTarjetasEquipos(datosFicticios, this); // 'this' para el listener general
        }
    }

    private void handleCrearNuevoEquipo() {
        // Lógica para mostrar un JDialog o cambiar a un panel de creación de equipo.
        // Por ejemplo, podrías tener un método en EquiposPanel para mostrar una vista de formulario.
        // equiposPanel.mostrarFormularioCrearEquipo();
        principalFrame.showMessage("Acción: Crear Nuevo Equipo (Implementar formulario/dialogo).", "Crear Equipo", JOptionPane.INFORMATION_MESSAGE);
        // Si se crea un equipo, se debería refrescar la lista:
        // loadEquiposOverviewData();
    }

    private void handleBuscarEquipos() {
        if (equiposPanel != null && equiposPanel.getEquiposPrincipalPanel() != null) {
            String termino = equiposPanel.getEquiposPrincipalPanel().getTerminoBusqueda();
            String torneoF = equiposPanel.getEquiposPrincipalPanel().getFiltroTorneoSeleccionado();
            String juegoF = equiposPanel.getEquiposPrincipalPanel().getFiltroJuegoSeleccionado();
            String jugadoresF = equiposPanel.getEquiposPrincipalPanel().getFiltroJugadoresSeleccionado();

            System.out.println("Buscando con: Termino='" + termino + "', Torneo='" + torneoF + "', Juego='" + juegoF + "', Jugadores='" + jugadoresF + "'");
            principalFrame.showMessage("Búsqueda/Filtrado activado (Implementar lógica de filtrado de datos).", "Buscar Equipos", JOptionPane.INFORMATION_MESSAGE);

            // Aquí iría la lógica para:
            // 1. Obtener los datos originales de los equipos (quizás de una clase de Modelo/Servicio).
            // 2. Aplicar los filtros y el término de búsqueda a esos datos.
            // 3. Llamar a equiposPanel.getVistaGeneralPanel().actualizarTarjetasEquipos(datosFiltrados, this);
            // Ejemplo (muy simplificado, solo para mostrar el flujo):
            // loadEquiposOverviewData(); // Por ahora, recarga todos como si no hubiera filtro
        }
    }
}