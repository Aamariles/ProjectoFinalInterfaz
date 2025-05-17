package co.edu.unbosque.controller;

import co.edu.unbosque.view.LoginFrame;
import co.edu.unbosque.view.PrincipalFrame; // Debe ser la versión refactorizada
import co.edu.unbosque.view.PanelNavegacion; // Import correcto
import co.edu.unbosque.view.PrincipalPanel;   // Usando PrincipalPanel para el dashboard
import co.edu.unbosque.view.TorneosPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private LoginFrame loginFrame;
    private PrincipalFrame principalFrame;

    private PrincipalPanel dashboardContentPanel; // Usando PrincipalPanel como el tipo para el dashboard
    private TorneosPanel torneosPanel;

    public Controller() {
        loginFrame = new LoginFrame();
        principalFrame = new PrincipalFrame(); // DEBE ser la versión refactorizada

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
                // Asumiendo que LoginFrame tiene getLoginPanel() y LoginPanel tiene LOGIN_COMMAND
                if (loginFrame.getLoginPanel() != null &&
                        e.getActionCommand().equals(loginFrame.getLoginPanel().LOGIN_COMMAND)) {
                    handleLoginAttempt();
                } else {
                    System.err.println("LoginPanel no disponible o comando de login desconocido: " + e.getActionCommand());
                }
            }
        });
    }

    private void handleLoginAttempt() {
        String user = loginFrame.getUsername();
        String pass = loginFrame.getPassword();

        if ("admin".equals(user) && "123".equals(pass)) {
            loginFrame.dispose();
            principalFrame.setVisible(true);
            setupPrincipalFrameNavigation();
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
        principalFrame.addNavigationListeners(this);
        showDashboardView(); // Mostrar el dashboard como vista inicial
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        PanelNavegacion navPanel = principalFrame.getNavigationPanel();

        if (navPanel == null) {
            System.err.println("Panel de Navegación (PanelNavegacion) no encontrado en PrincipalFrame.");
            return;
        }

        switch (command) {
            case PanelNavegacion.NAV_DASHBOARD_COMMAND:
                showDashboardView();
                break;
            case PanelNavegacion.NAV_TOURNAMENTS_COMMAND:
                showTournamentsView();
                break;
            case PanelNavegacion.NAV_TEAMS_COMMAND:
                principalFrame.showMessage("Sección 'Equipos' no implementada todavía.", "En Desarrollo", JOptionPane.INFORMATION_MESSAGE);
                navPanel.setActiveButton(navPanel.getBtnNavTeams());
                break;
            case PanelNavegacion.NAV_MATCHES_COMMAND:
                principalFrame.showMessage("Sección 'Partidas' no implementada todavía.", "En Desarrollo", JOptionPane.INFORMATION_MESSAGE);
                navPanel.setActiveButton(navPanel.getBtnNavMatches());
                break;
            case PanelNavegacion.NAV_REPORTS_COMMAND:
                principalFrame.showMessage("Sección 'Reportes' no implementada todavía.", "En Desarrollo", JOptionPane.INFORMATION_MESSAGE);
                navPanel.setActiveButton(navPanel.getBtnNavReports());
                break;
            case PanelNavegacion.NAV_SETTINGS_COMMAND:
                principalFrame.showMessage("Sección 'Configuración' no implementada todavía.", "En Desarrollo", JOptionPane.INFORMATION_MESSAGE);
                navPanel.setActiveButton(navPanel.getBtnNavSettings());
                break;
            default:
                System.out.println("Comando no reconocido por el Controller: " + command);
                break;
        }
    }

    private void showDashboardView() {
        if (dashboardContentPanel == null) {
            dashboardContentPanel = new PrincipalPanel(); // Usando PrincipalPanel
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

    private void showTournamentsView() {
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

    private void loadDashboardData() {
        System.out.println("Cargando datos del Dashboard...");
        if (dashboardContentPanel != null) {
            // PrincipalPanel (tu DashboardContentPanel) tiene estos métodos
            dashboardContentPanel.updateSummaryCards("Próximo: Alfa vs Omega", "5 Activos", "El Gran Maestro");
            Object[][] matchesData = {
                    {"Equipo Sol vs. Equipo Luna", "Hoy", "En vivo"},
                    {"Equipo Estrella vs. Equipo Cometa", "Ayer", "Finalizado (3-0)"}
            };
            dashboardContentPanel.updateRecentMatchesTable(matchesData);
        }
    }

    private void loadTournamentsData() {
        System.out.println("Cargando datos de Torneos...");
        if (torneosPanel != null && torneosPanel.getGeneralInfoPanel() != null) {
            java.util.List<Object[]> sampleTournamentData = new java.util.ArrayList<>();
            sampleTournamentData.add(new Object[]{"Copa Intergaláctica", "En curso", "/imagenes/logo_rocketleague.png", "32 naves", "Activo", "May 2025 - Jun 2025", true});
            sampleTournamentData.add(new Object[]{"Desafío Orbital", "Registrando", "/imagenes/logo_generic_game.png", "64 pilotos", "Próximo", "Jul 2025 - Ago 2025", false});

            ActionListener tournamentDetailsListener = ae -> {
                JButton button = (JButton) ae.getSource();
                String tournamentName = "N/A";
                if(button.getClientProperty("tournamentName") != null){
                    tournamentName = button.getClientProperty("tournamentName").toString();
                }
                principalFrame.showMessage("Mostrar detalles para: " + tournamentName, "Detalles de Torneo", JOptionPane.INFORMATION_MESSAGE);
            };
            torneosPanel.getGeneralInfoPanel().refreshTournamentCards(sampleTournamentData, tournamentDetailsListener);
        }
    }
}