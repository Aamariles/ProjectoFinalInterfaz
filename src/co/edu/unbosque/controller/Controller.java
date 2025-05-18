package co.edu.unbosque.controller;
import co.edu.unbosque.model.ModelFacade;
import co.edu.unbosque.model.GameDTO;
import co.edu.unbosque.model.TeamDTO;
import co.edu.unbosque.view.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
public class Controller implements ActionListener {
    private LoginFrame loginFrame;
    private PrincipalFrame principalFrame;
    private ModelFacade modelFacade;
    private PrincipalPanel dashboardContentPanel;
    private TournamentsPanel tournamentsPanel;
    private EquiposPanel equiposPanel;
    private PartidasPanel partidasPanel;
    private SimpleDateFormat viewDateFormatWithTime = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private SimpleDateFormat viewDateFormatOnlyDate = new SimpleDateFormat("dd/MM/yyyy");
    public Controller() {
        modelFacade = new ModelFacade();
        loginFrame = new LoginFrame();
        principalFrame = new PrincipalFrame();
        attachLoginListeners();
        principalFrame.setVisible(false);
    }
    public void showLogin() {
        loginFrame.setVisible(true);
    }
    private void attachLoginListeners() {
        loginFrame.addLoginListener(e -> {
            if (loginFrame.getLoginPanel() != null &&
                    e.getActionCommand().equals(loginFrame.getLoginPanel().LOGIN_COMMAND)) {
                handleLoginAttempt();
            } else if (e.getActionCommand().equals("LOGIN_SUBMIT")){
                System.err.println("Advertencia: loginFrame.getLoginPanel() podría ser null o LOGIN_COMMAND no coincide, usando 'LOGIN_SUBMIT' como fallback.");
                handleLoginAttempt();
            }
        });
    }
    private void handleLoginAttempt() {
        String user = loginFrame.getUsername();
        String pass = loginFrame.getPassword();
        if (modelFacade.validateUserCredentials(user, pass)) {
            loginFrame.dispose();
            principalFrame.setVisible(true);
            setupPrincipalFrameNavigation();
        } else {
            loginFrame.showMessage("Credenciales incorrectas. Por favor, inténtelo de nuevo.",
                    "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            loginFrame.clearFields();
            if (loginFrame.getUsernameField() != null) {
                loginFrame.getUsernameField().requestFocusInWindow();
            }
        }
    }
    private void setupPrincipalFrameNavigation() {
        principalFrame.addNavigationListeners(this);
        showDashboardView();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        PanelNavegacion navPanel = principalFrame.getNavigationPanel();
        boolean comandoDeNavegacionPrincipal = true;
        if (navPanel == null && command.startsWith("NAV_")) {
            System.err.println("Panel de Navegación no encontrado en PrincipalFrame.");
            return;
        }
        switch (command) {
            case PanelNavegacion.NAV_DASHBOARD_COMMAND: showDashboardView(); break;
            case PanelNavegacion.NAV_TOURNAMENTS_COMMAND: showTournamentsView(); break;
            case PanelNavegacion.NAV_TEAMS_COMMAND: showEquiposView(); break;
            case PanelNavegacion.NAV_MATCHES_COMMAND: showPartidasView(); break;
            case PanelNavegacion.NAV_REPORTS_COMMAND:
                principalFrame.showMessage("Sección 'Reportes' no implementada.", "En Desarrollo", JOptionPane.INFORMATION_MESSAGE);
                if (navPanel != null) navPanel.setActiveButton(navPanel.getBtnNavReports());
                break;
            case PanelNavegacion.NAV_SETTINGS_COMMAND:
                principalFrame.showMessage("Sección 'Configuración' no implementada.", "En Desarrollo", JOptionPane.INFORMATION_MESSAGE);
                if (navPanel != null) navPanel.setActiveButton(navPanel.getBtnNavSettings());
                break;
            default:
                comandoDeNavegacionPrincipal = false;
                break;
        }
        if (comandoDeNavegacionPrincipal) return;
        if (command.equals(EquiposPrincipalPanel.ACCION_CREAR_EQUIPO)) {
            handleCrearNuevoEquipo();
        } else if (command.equals(EquiposPrincipalPanel.ACCION_BUSCAR_EQUIPOS)) {
            handleBuscarEquipos();
        }
        else if (command.equals(PartidasVistaGeneralPanel.ACCION_AGENDAR_PARTIDA)) {
            handleAgendarNuevaPartida();
        } else if (command.equals(PartidasVistaGeneralPanel.ACCION_BUSCAR_PARTIDAS)) {
            handleBuscarPartidas();
        }
        else if (command.startsWith(PartidasVistaGeneralPanel.ACCION_EDITAR_PARTIDA_TABLA)) {
            String partidaId = command.substring(PartidasVistaGeneralPanel.ACCION_EDITAR_PARTIDA_TABLA.length());
            handleEditarPartida(partidaId);
        } else if (command.startsWith(PartidasVistaGeneralPanel.ACCION_REPORTAR_RESULTADO_TABLA)) {
            String partidaId = command.substring(PartidasVistaGeneralPanel.ACCION_REPORTAR_RESULTADO_TABLA.length());
            handleReportarResultado(partidaId);
        }
        else if (command.startsWith(PartidaDetallePanel.ACCION_REPROGRAMAR_FECHA)) {
            handleReprogramarFechaPartida(command.substring(PartidaDetallePanel.ACCION_REPROGRAMAR_FECHA.length()));
        } else if (command.startsWith(PartidaDetallePanel.ACCION_GUARDAR_RESULTADO)) {
            handleGuardarResultadoPartida(command.substring(PartidaDetallePanel.ACCION_GUARDAR_RESULTADO.length()));
        } else if (command.startsWith(PartidaDetallePanel.ACCION_ADJUNTAR_EVIDENCIA)) {
            handleAdjuntarEvidenciaPartida(command.substring(PartidaDetallePanel.ACCION_ADJUNTAR_EVIDENCIA.length()));
        } else if (command.startsWith(PartidaDetallePanel.ACCION_CONFIRMAR_RESULTADO_DETALLE)) {
            handleConfirmarResultadoDetallePartida(command.substring(PartidaDetallePanel.ACCION_CONFIRMAR_RESULTADO_DETALLE.length()));
        } else {
            System.out.println("Comando de panel no reconocido por el Controller: '" + command + "'");
        }
    }
    private void showDashboardView() {
        if (dashboardContentPanel == null) dashboardContentPanel = new PrincipalPanel();
        if (!(principalFrame.getCurrentCenterPanel() instanceof PrincipalPanel)) {
            principalFrame.setCenterPanel(dashboardContentPanel);
        }
        principalFrame.setMainContentTitle("Dashboard");
        if (principalFrame.getNavigationPanel() != null)
            principalFrame.getNavigationPanel().setActiveButton(principalFrame.getNavigationPanel().getBtnNavDashboard());
        loadDashboardData();
    }
    private void showTournamentsView() {
        if (tournamentsPanel == null) tournamentsPanel = new TournamentsPanel();
        if (!(principalFrame.getCurrentCenterPanel() instanceof TournamentsPanel)) {
            principalFrame.setCenterPanel(tournamentsPanel);
        }
        principalFrame.setMainContentTitle("Torneos");
        if (principalFrame.getNavigationPanel() != null)
            principalFrame.getNavigationPanel().setActiveButton(principalFrame.getNavigationPanel().getBtnNavTournaments());
        loadTournamentsData();
    }
    private void showEquiposView() {
        if (equiposPanel == null) equiposPanel = new EquiposPanel(this);
        if (!(principalFrame.getCurrentCenterPanel() instanceof EquiposPanel)) {
            principalFrame.setCenterPanel(equiposPanel);
        }
        principalFrame.setMainContentTitle("Gestión de Equipos");
        if (principalFrame.getNavigationPanel() != null)
            principalFrame.getNavigationPanel().setActiveButton(principalFrame.getNavigationPanel().getBtnNavTeams());
        loadEquiposOverviewData();
    }
    private void showPartidasView() {
        if (partidasPanel == null) partidasPanel = new PartidasPanel(this);
        if (!(principalFrame.getCurrentCenterPanel() instanceof PartidasPanel)) {
            principalFrame.setCenterPanel(partidasPanel);
        }
        principalFrame.setMainContentTitle("Gestión de Partidas");
        if (principalFrame.getNavigationPanel() != null)
            principalFrame.getNavigationPanel().setActiveButton(principalFrame.getNavigationPanel().getBtnNavMatches());
        loadPartidasOverviewData();
    }
    private void loadDashboardData() {
        if (dashboardContentPanel != null && modelFacade != null) {
            Object[] summaryData = modelFacade.getDashboardSummaryData();
            if (summaryData != null && summaryData.length >= 3) {
                dashboardContentPanel.updateSummaryCards(
                        summaryData[0] != null ? summaryData[0].toString() : "N/A",
                        summaryData[1] != null ? summaryData[1].toString() : "N/A",
                        summaryData[2] != null ? summaryData[2].toString() : "N/A"
                );
            }
            List<Object[]> matchesDataList = modelFacade.getDashboardRecentMatches();
            Object[][] matchesDataArray = null;
            if (matchesDataList != null) {
                matchesDataArray = new Object[matchesDataList.size()][];
                for (int i = 0; i < matchesDataList.size(); i++) {
                    matchesDataArray[i] = matchesDataList.get(i);
                }
            } else {
                matchesDataArray = new Object[0][0];
            }
            dashboardContentPanel.updateRecentMatchesTable(matchesDataArray);
        }
    }
    private void loadTournamentsData() {
        if (tournamentsPanel != null && tournamentsPanel.getGeneralInfoPanel() != null && modelFacade != null) {
            List<Object[]> tournamentDataList = modelFacade.getTournamentsListForOverview();
            ActionListener tournamentDetailsListener = ae -> {
                JButton button = (JButton) ae.getSource();
                String tournamentName = "N/A";
                if(button.getClientProperty("tournamentName") != null){
                    tournamentName = button.getClientProperty("tournamentName").toString();
                }
                principalFrame.showMessage("Detalles del Torneo (Controller): " + tournamentName, "Info Torneo", JOptionPane.INFORMATION_MESSAGE);
            };
            tournamentsPanel.getGeneralInfoPanel().refreshTournamentCards(tournamentDataList, tournamentDetailsListener);
        }
    }
    private void loadEquiposOverviewData() {
        if (equiposPanel != null && equiposPanel.getEquiposPrincipalPanel() != null && modelFacade != null) {
            List<TeamDTO> teamDTOs = modelFacade.getAllTeamDTOs();
            List<Object[]> viewData = new ArrayList<>();
            if (teamDTOs != null) {
                for (TeamDTO dto : teamDTOs) {
                    String coachName = (dto.getCoach() != null && dto.getCoach().getName() != null) ? dto.getCoach().getName() : "N/A";
                    viewData.add(new Object[]{
                            dto.getTeamName(),
                            "/imagenes/logo_equipo_default.png",
                            0,
                            "Ubicación N/A",
                            dto.getTournamentFormat(),
                            dto.getTeamName()
                    });
                }
            }
            equiposPanel.getEquiposPrincipalPanel().actualizarTarjetasEquipos(viewData, this);
        }
    }
    private void loadPartidasOverviewData() {
        if (partidasPanel != null && partidasPanel.getVistaGeneralPanel() != null && modelFacade != null) {
            List<GameDTO> matchDTOs = modelFacade.getAllMatchDTOs();
            List<Object[]> viewData = new ArrayList<>();
            if (matchDTOs != null) {
                for (GameDTO dto : matchDTOs) {
                    String enfrentamiento = dto.getGameTitle();
                    if (dto.getTeamsList() != null && !dto.getTeamsList().isEmpty()) {
                        enfrentamiento = dto.getTeamsList().stream()
                                .map(team -> team != null ? team.getTeamName() : "Equipo Desconocido")
                                .collect(Collectors.joining(" vs "));
                    }
                    String fechaStr = "N/A";
                    if (dto.getDate() != null) {
                        try {
                            fechaStr = viewDateFormatOnlyDate.format(Date.from(dto.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                        } catch (Exception ex) {
                            System.err.println("Error formateando fecha de partida: " + ex.getMessage());
                        }
                    }
                    viewData.add(new Object[]{
                            dto.getGameTitle(),
                            "Torneo Placeholder",
                            "Ronda Placeholder",
                            enfrentamiento,
                            fechaStr,
                            "Estado Placeholder",
                            "Resultado Placeholder"
                    });
                }
            }
            partidasPanel.getVistaGeneralPanel().actualizarTablaPartidas(viewData);
        }
    }
    private void handleCrearNuevoEquipo() {
        JTextField teamNameField = new JTextField();
        JTextField formatField = new JTextField();
        Object[] message = {
                "Nombre del Equipo:", teamNameField,
                "Juego Principal/Formato:", formatField
        };
        int option = JOptionPane.showConfirmDialog(principalFrame, message, "Crear Nuevo Equipo", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String teamName = teamNameField.getText();
            String format = formatField.getText();
            if (teamName != null && !teamName.trim().isEmpty() && format != null && !format.trim().isEmpty()) {
                TeamDTO newTeam = new TeamDTO(format, teamName, null);
                if (modelFacade.addTeam(newTeam)) {
                    principalFrame.showMessage("Equipo '" + teamName + "' creado exitosamente.", "Equipo Creado", JOptionPane.INFORMATION_MESSAGE);
                    loadEquiposOverviewData();
                } else {
                    principalFrame.showMessage("Error al crear el equipo (quizás ya existe).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                principalFrame.showMessage("Nombre del equipo y formato no pueden estar vacíos.", "Datos Inválidos", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    private void handleBuscarEquipos() {
        if (equiposPanel != null && equiposPanel.getEquiposPrincipalPanel() != null) {
            String termino = equiposPanel.getEquiposPrincipalPanel().getTerminoBusqueda();
            principalFrame.showMessage("Buscando equipos con término: '" + termino + "' (Funcionalidad de filtrado real pendiente).", "Buscar Equipos", JOptionPane.INFORMATION_MESSAGE);
            loadEquiposOverviewData();
        }
    }
    private void handleAgendarNuevaPartida() {
        principalFrame.showMessage("Acción: Agendar Nueva Partida (Implementar formulario/dialogo).", "Agendar Partida", JOptionPane.INFORMATION_MESSAGE);
    }
    private void handleBuscarPartidas() {
        if (partidasPanel != null && partidasPanel.getVistaGeneralPanel() != null) {
            String termino = partidasPanel.getVistaGeneralPanel().getTerminoBusquedaGlobal();
            principalFrame.showMessage("Buscando partidas con término: '" + termino + "' (Funcionalidad de filtrado real pendiente).", "Buscar Partidas", JOptionPane.INFORMATION_MESSAGE);
            loadPartidasOverviewData();
        }
    }
    private void handleEditarPartida(String partidaId) {
        principalFrame.showMessage("Acción: Editar Partida ID " + partidaId + "\n(Implementar lógica de edición).", "Editar Partida", JOptionPane.INFORMATION_MESSAGE);
        if(partidasPanel != null) {
            partidasPanel.mostrarDetallePartida(partidaId);
        }
    }
    private void handleReportarResultado(String partidaId) {
        principalFrame.showMessage("Acción: Reportar resultado para Partida ID " + partidaId + "\n(Implementar lógica).", "Reportar Resultado", JOptionPane.INFORMATION_MESSAGE);
        if(partidasPanel != null) {
            partidasPanel.mostrarDetallePartida(partidaId);
        }
    }
    private void handleReprogramarFechaPartida(String partidaId) {
        principalFrame.showMessage("Acción: Reprogramar Fecha para Partida ID " + partidaId + "\n(Implementar lógica).", "Reprogramar Partida", JOptionPane.INFORMATION_MESSAGE);
    }
    private void handleGuardarResultadoPartida(String partidaId) {
        principalFrame.showMessage("Acción: Guardar Resultado para Partida ID " + partidaId + "\n(Implementar lógica).", "Guardar Resultado", JOptionPane.INFORMATION_MESSAGE);
    }
    private void handleAdjuntarEvidenciaPartida(String partidaId) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(principalFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            principalFrame.showMessage("Archivo seleccionado: " + selectedFile.getAbsolutePath() + "\nPara Partida ID " + partidaId + " (Implementar lógica de guardado).", "Adjuntar Evidencia", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void handleConfirmarResultadoDetallePartida(String partidaId) {
        principalFrame.showMessage("Acción: Confirmar Resultado (Admin) para Partida ID " + partidaId + "\n(Implementar lógica de confirmación).", "Confirmar Resultado", JOptionPane.INFORMATION_MESSAGE);
    }
}